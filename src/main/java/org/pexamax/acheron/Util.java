// Package and Imports
package org.pexamax.acheron;

// Encoding and decoding
import java.nio.charset.StandardCharsets;
import java.util.Base64;

// Argon2id - https://javadoc.io/static/de.mkammerer/argon2-jvm/2.8/de/mkammerer/argon2/package-summary.html
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.HashResult;

// For Cipher's exceptions
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// Cipher construction and encryption/decryption
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

// Bouncy Castle
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import org.bouncycastle.crypto.agreement.X25519Agreement;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;

import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;

import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;

import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;

import org.bouncycastle.crypto.digests.SHA256Digest;

public class Util {
    // Constants for Argon2 parameters
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 65535; // 64 MB
    private static final int PARALLELISM = 1; // Single-threaded
    private static final de.mkammerer.argon2.Argon2Version ARGON2_VERSION = de.mkammerer.argon2.Argon2Version.V13; // Use Argon2 v1.3; has 2 values: V10 and V13

    private static final int X25519_SHARED_KEY_LENGTH = 32; // 32 bytes for X25519 shared key length

    private static final int AES_GCM_256_KEY_LENGTH = 32;
    private static final int AES_GCM_256_IV_LENGTH = 12;

    private static final Argon2 argon2 = Argon2Factory.create();
    private static final Argon2Advanced argon2Advanced = Argon2Factory.createAdvanced();

    // Convert byte array to UTF_8 string
    public static String bytesToUTF8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // Convert UTF_8 string to byte array
    public static byte[] utf8ToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    // Convert byte array to Base64 string
    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    // Covert Base64 string to byte array
    public static byte[] base64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    // Take plain password, return hash
    public static String hashPassword(String password) {
        // argon2.hash(int iterations, int memory, int parallelism, char[] password)
        // iterations: number of iterations (or time cost). Higher means more hashing time and better resistance to brute-force attacks.
        // memory: memory usage in kilobytes (here, 65536 KB = 64 MB). More memory makes it harder for attackers to use GPUs or ASICs.
        // parallelism: number of parallel threads or compute lanes used. 1 means single-threaded hashing.
        // password: nah, you know what this is.
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
    }

    // Hash password with Argon2 and return both raw byte and encoded representation
    public static HashResult hashPassword(String password, int hashLength) {
        int saltLength = 12;
        byte[] salt = argon2Advanced.generateSalt(saltLength); // Generate a random salt of 12 bytes
        // return both raw byte and the encoded representation
        return argon2Advanced.hashAdvanced(ITERATIONS, MEMORY, PARALLELISM, utf8ToBytes(password), salt, hashLength, ARGON2_VERSION);
    }

    public static HashResult hashPassword(String password, byte[] salt, int hashLength) {
        // return both raw byte and the encoded representation
        return argon2Advanced.hashAdvanced(ITERATIONS, MEMORY, PARALLELISM, utf8ToBytes(password), salt, hashLength, ARGON2_VERSION);
    }


    public static boolean verifyPassword(String password, String hash) {
        // argon2.verify(String hash, char[] password)
        // hash: the hashed password to verify againstjavax.crypto.spec
        // password: the plain text password to verify
        return argon2.verify(hash, password.toCharArray());
    }


    // Generate secret key and IV from password
    public static byte[] encryptPrivateKey(String password, byte[] privateKeyRaw)  {
        int secretKeyLength = 16; // 16 bytes for secret key
        int IVLength = 12; // 12 bytes for IV
        int hashLength = secretKeyLength + IVLength; // 28 bytes for secret key

        byte[] salt = argon2Advanced.generateSalt(16); // 16-byte long salt for Argon2
        HashResult passwordHashResult = hashPassword(password, salt, hashLength);
        byte[] rawPassowrdHash = passwordHashResult.getRaw();

        byte[] secretKey = new byte[secretKeyLength]; // 32 bytes for AES-GCM secret key
        System.arraycopy(rawPassowrdHash, 0, secretKey, 0, secretKey.length); // Copy first 32 bytes for secret key from 

        byte[] IV = new byte[IVLength]; // 12 bytes for IV
        System.arraycopy(rawPassowrdHash, secretKey.length, IV, 0, IV.length); // Copy next 12 bytes for IV from the raw password hash

        // Initialize AES-GCM cipher
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // AES in GCM mode with no padding
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
            GCMParameterSpec IVSpec = new GCMParameterSpec(128, IV); // 128-bit tag (cryptographic checksum) length
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IVSpec);

            // Encrypt the private key
            byte[] encryptedPrivateKey = cipher.doFinal(privateKeyRaw); // Changed from UTF_8 to Base64 cuz privateKey is Base64 decoded.

            // Combine the encrypted private key and salt into a single byte array
            byte[] combinedCipherAndSalt = new byte[encryptedPrivateKey.length + salt.length];
            System.arraycopy(encryptedPrivateKey, 0, combinedCipherAndSalt, 0, encryptedPrivateKey.length);
            System.arraycopy(salt, 0, combinedCipherAndSalt, encryptedPrivateKey.length, salt.length);
            
            // Return the Base64-encoded string of the combined cipher and salt
            return combinedCipherAndSalt;

        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            return null;
        } catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
            return null;
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
            return null;
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
            return null;
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
            return null;
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
            return null;
        }
    }

    public static byte[] decryptPrivateKey(String password, byte[] encPrivateKeyRaw) {
        int secretKeyLength = 16; // 16 bytes for secret key
        int IVLength = 12; // 12 bytes for IV
        int hashLength = secretKeyLength + IVLength; // 28 bytes for secret key

        byte[] salt = new byte[16]; // 16 bytes for salt
        System.arraycopy(encPrivateKeyRaw, encPrivateKeyRaw.length - salt.length, salt, 0, salt.length); // Extract the salt from the end of the byte array

        byte[] PRKCipher = new byte[encPrivateKeyRaw.length - salt.length];
        System.arraycopy(encPrivateKeyRaw, 0, PRKCipher, 0, PRKCipher.length); // Extract the cipher from the beginning of the byte array

        HashResult passwordHashResult = hashPassword(password, salt, hashLength);
        byte[] rawPassowrdHash = passwordHashResult.getRaw();

        byte[] secretKey = new byte[secretKeyLength];
        System.arraycopy(rawPassowrdHash, 0, secretKey, 0, secretKey.length); 

        byte[] IV = new byte[IVLength];
        System.arraycopy(rawPassowrdHash, secretKey.length, IV, 0, IV.length); 

        // Initialize AES-GCM cipher
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // AES in GCM mode with no padding
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
            GCMParameterSpec IVSpec = new GCMParameterSpec(128, IV); // 128-bit tag length
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IVSpec);

            // Decrypt the private key
            byte[] decryptedPrivateKey = cipher.doFinal(PRKCipher);

            return decryptedPrivateKey;

        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            return null;
        } catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
            return null;
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
            return null;
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
            return null;
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
            return null;
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
            return null;
       }
    }

    // public and private key generator -- Curve25519 (bouncy castle)
    public static AsymmetricCipherKeyPair generateX25519KeyPair() {
        X25519KeyPairGenerator keyPairGenerator = new X25519KeyPairGenerator();
        keyPairGenerator.init(new X25519KeyGenerationParameters(new SecureRandom()));

        // Generate the key pair
        return keyPairGenerator.generateKeyPair();
    }

    // Generate X25519 key pair from Ed25519 key pair
    public static AsymmetricCipherKeyPair generateX25519KeyPair(byte[] edPrivateKey) {
        // Create X25519 private key parameters directly from the encoded Ed25519 private key.
        // Bouncy Castle handles the underlying conversion internally.
        X25519PrivateKeyParameters xPrivateKey = new X25519PrivateKeyParameters(edPrivateKey);

        // Create X25519 public key parameters directly from the encoded Ed25519 public key.
        // Bouncy Castle handles the underlying conversion internally.
        X25519PublicKeyParameters xPublicKey = xPrivateKey.generatePublicKey();

        // Return the new Curve25519 key pair
        return new AsymmetricCipherKeyPair(xPublicKey, xPrivateKey);
    } 

    // Get the public key from the key pair in bytearray. Use the bytesToBase64 to convert to String
    public static byte[] getX25519PublicKey(AsymmetricCipherKeyPair X25519keyPair) {
        X25519PublicKeyParameters publicKey = (X25519PublicKeyParameters) X25519keyPair.getPublic();
        return publicKey.getEncoded();
    }

    // Get the private key from the key pair in bytearray. Use the bytesToBase64 to convert to String
    public static byte[] getX25519PrivateKey(AsymmetricCipherKeyPair X25519keyPair) {
        X25519PrivateKeyParameters privateKey = (X25519PrivateKeyParameters) X25519keyPair.getPrivate();
        return privateKey.getEncoded();
    }

    // Curve25519 Shared Key Generator -- takes Base64 encoded private and public keys
    public static byte[] generateX25519SharedSecret(byte[] privateKeyRaw, byte[] publicKeyRaw) {
        if (privateKeyRaw.length != X25519_SHARED_KEY_LENGTH || publicKeyRaw.length != X25519_SHARED_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid key public or private keys length.");
        }

        // Create parameters for the shared key generation
        X25519PrivateKeyParameters privateKeyParams = new X25519PrivateKeyParameters(privateKeyRaw, 0);
        X25519PublicKeyParameters publicKeyParams = new X25519PublicKeyParameters(publicKeyRaw, 0);

        // Generate the shared key using the private and public keys
        byte[] sharedSecret = new byte[X25519_SHARED_KEY_LENGTH];
        X25519Agreement agreement = new X25519Agreement();
        agreement.init(privateKeyParams);
        agreement.calculateAgreement(publicKeyParams, sharedSecret, 0);

        return sharedSecret;
    }

    public static AsymmetricCipherKeyPair generateEd25519KeyPair() {
        Ed25519KeyPairGenerator keyPairGenerator = new Ed25519KeyPairGenerator();

        // Initialize the generator with a secure random number generator.
        // A cryptographically strong random source is crucial for key generation.
        keyPairGenerator.init(new Ed25519KeyGenerationParameters(new SecureRandom()));

        // Generate the key pair. This returns an AsymmetricCipherKeyPair object,
        // which holds both the private and public key parameters.
        return keyPairGenerator.generateKeyPair();
    }

    // Get the public key from the key pair in bytearray. Use the bytesToBase64 to convert to String
    public static byte[] getEd25519PublicKey(AsymmetricCipherKeyPair Ed25519keyPair) {
        Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) Ed25519keyPair.getPublic();
        return publicKey.getEncoded();
    }

    // Get the private key from the key pair in bytearray. Use the bytesToBase64 to convert to String
    public static byte[] getEd25519PrivateKey(AsymmetricCipherKeyPair Ed25519keyPair) {
        Ed25519PrivateKeyParameters privateKey = (Ed25519PrivateKeyParameters) Ed25519keyPair.getPrivate();
        return privateKey.getEncoded();
    }

    // Returns a symmetric key that contains 32-byte key and 12-byte IV
    public static byte[] generateSymmetricKey(byte[] sharedSecret) {
        if (sharedSecret == null || sharedSecret.length == 0) {
            System.err.println("Shared secret cannot be null or empty for HKDF derivation.");
            return null;
        }

        // We need 32 bytes for the AES-256 key and 12 bytes for the AES-GCM IV.
        // Total output length required is 32 + 12 = 44 bytes.
        int outputLength = AES_GCM_256_KEY_LENGTH + AES_GCM_256_IV_LENGTH;

        // Initialize HKDFBytesDerivationFunction with SHA256 digest.
        // HKDF consists of a "extract" part (PRF) and an "expand" part.
        // Here, the sharedSecret is the Input Keying Material (IKM).
        DerivationFunction kdf = new HKDFBytesGenerator(new SHA256Digest());

        // HKDFParameters:
        // 1. IKM (Input Keying Material): The shared secret from X25519.
        // 2. Salt: An optional non-secret random value. Recommended for real-world scenarios
        //    to provide domain separation and strengthen security, but can be null for simplicity
        //    if no specific salt is available. For production, always use a unique, random salt.
        // 3. Info: Optional context-specific information. Also recommended for domain separation
        //    (e.g., "AES-GCM-256 key and IV for message encryption"). Can be null here.
        DerivationParameters hkdfParams = new HKDFParameters(sharedSecret, null, null);
        kdf.init(hkdfParams);

        // Allocate a buffer to hold the derived bytes.
        byte[] derivedBytes = new byte[outputLength];

        // Derive the bytes and fill the buffer.
        kdf.generateBytes(derivedBytes, 0, outputLength);

        return derivedBytes;
    }

    
    // Use Eliptic Curve Cryptography (ECC)
    //
    public static byte[] symEncrypt(byte[] symmetricKey, byte[] plaintext) {
        try {
            byte[] secretKey = new byte[AES_GCM_256_KEY_LENGTH];
            byte[] IV = new byte[AES_GCM_256_IV_LENGTH];

            System.arraycopy(symmetricKey, 0, secretKey, 0, AES_GCM_256_KEY_LENGTH); // Copy first 32 bytes for secret key
            System.arraycopy(symmetricKey, AES_GCM_256_KEY_LENGTH, IV, 0, AES_GCM_256_IV_LENGTH); // Copy next 12 bytes for IV

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // AES in GCM mode with no padding
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
            GCMParameterSpec IVSpec = new GCMParameterSpec(128, IV); // 128-bit tag (cryptographic checksum) length
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IVSpec);

            // Encrypt the encrypted message
            return cipher.doFinal(plaintext);

        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            return null;
        } catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
            return null;
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
            return null;
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
            return null;
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
            return null;
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
            return null;
        }
    }

    public static byte[] symDecrypt(byte[] symmetricKey, byte[] encMessage) {
        try {
            byte[] secretKey = new byte[AES_GCM_256_KEY_LENGTH];
            byte[] IV = new byte[AES_GCM_256_IV_LENGTH];

            System.arraycopy(symmetricKey, 0, secretKey, 0, AES_GCM_256_KEY_LENGTH); // Copy first 32 bytes for secret key
            System.arraycopy(symmetricKey, AES_GCM_256_KEY_LENGTH, IV, 0, AES_GCM_256_IV_LENGTH); // Copy next 12 bytes for IV

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // AES in GCM mode with no padding
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
            GCMParameterSpec IVSpec = new GCMParameterSpec(128, IV); // 128-bit tag (cryptographic checksum) length
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IVSpec);

            // Encrypt the encrypted message
            return cipher.doFinal(encMessage);

        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            return null;
        } catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
            return null;
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
            return null;
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
            return null;
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
            return null;
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
            return null;
        }
    }

    // public static byte[] signEncMessage(byte[] edPrivateKey, byte[] encMessage) {
    //     byte[] privateKey = base64ToBytes(Base64ed25519PrivateKey);
    //
    // }
    //
    // public static <type> AsymEncrypt();
    // public static <type> AsymDecrypt();
}
