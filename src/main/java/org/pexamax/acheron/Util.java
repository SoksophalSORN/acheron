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

// Cipher construction and encryption/decryption
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Util {
    // Constants for Argon2 parameters
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 65535; // 64 MB
    private static final int PARALLELISM = 1; // Single-threaded
    private static final de.mkammerer.argon2.Argon2Version ARGON2_VERSION = de.mkammerer.argon2.Argon2Version.V13; // Use Argon2 v1.3; has 2 values: V10 and V13

    private static final Argon2 argon2 = Argon2Factory.create();
    private static final Argon2Advanced argon2Advanced = Argon2Factory.createAdvanced();

    // Convert byte array to UTF_8 string
    public static String bytesToUTF8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // Convert byte array to Base64 string
    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
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
        return argon2Advanced.hashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password.getBytes(StandardCharsets.UTF_8), salt, hashLength, ARGON2_VERSION);
    }

    public static HashResult hashPassword(String password, byte[] salt, int hashLength) {
        // return both raw byte and the encoded representation
        return argon2Advanced.hashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password.getBytes(StandardCharsets.UTF_8), salt, hashLength, ARGON2_VERSION);
    }


    public static boolean verifyPassword(String password, String hash) {
        // argon2.verify(String hash, char[] password)
        // hash: the hashed password to verify againstjavax.crypto.spec
        // password: the plain text password to verify
        return argon2.verify(hash, password.toCharArray());
    }


    // Generate secret key and IV from password
    public static String encryptPrivateKey(String password, String privateKey)  {
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
            byte[] encryptedPrivateKey = cipher.doFinal(privateKey.getBytes(StandardCharsets.UTF_8));

            // Combine the encrypted private key and salt into a single byte array
            byte[] combinedCipherAndSalt = new byte[encryptedPrivateKey.length + salt.length];
            System.arraycopy(encryptedPrivateKey, 0, combinedCipherAndSalt, 0, encryptedPrivateKey.length);
            System.arraycopy(salt, 0, combinedCipherAndSalt, encryptedPrivateKey.length, salt.length);
            
            // Return the Base64-encoded string of the combined cipher and salt
            return bytesToBase64(combinedCipherAndSalt);

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

    public static String decryptPrivateKey(String password, String encPrivateKey) {
        int secretKeyLength = 16; // 16 bytes for secret key
        int IVLength = 12; // 12 bytes for IV
        int hashLength = secretKeyLength + IVLength; // 28 bytes for secret key

        byte[] combinedCipherAndSalt = Base64.getDecoder().decode(encPrivateKey);

        byte[] salt = new byte[16]; // 16 bytes for salt
        System.arraycopy(combinedCipherAndSalt, combinedCipherAndSalt.length - salt.length, salt, 0, salt.length); // Extract the salt from the end of the byte array

        byte[] PRKCipher = new byte[combinedCipherAndSalt.length - salt.length];
        System.arraycopy(combinedCipherAndSalt, 0, PRKCipher, 0, PRKCipher.length); // Extract the cipher from the beginning of the byte array

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

            return bytesToUTF8(decryptedPrivateKey);

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

    // public and private key generator -- will use secp256k1 or Curve25519 (bouncy castle)
    // sharedkey generator -- will Curve25519 (bouncy castle)
    // Ed25519 for Curve25519 digital signatures

    // Use Eliptic Curve Cryptography (ECC)
    // public static <type> AsymEncrypt();
    // public static <type> AsymDecrypt();
    //
    // public static <type> SymEncrypt();
    // public static <type> SymDecrypt();
}
