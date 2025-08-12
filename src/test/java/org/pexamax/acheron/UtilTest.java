// Package and Imports
// Test command: ./mvnw test -Dtest=UtilTest

package org.pexamax.acheron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;

import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;

import org.bouncycastle.crypto.params.HKDFParameters;

import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.DerivationParameters;


public class UtilTest {

    
    @Test // Test passed
    void hashTest() {
        String input = "Never Gonna Give You Up, Never Gonna Let You Down";
        String salt = "HkYDdC7FvVElPdq8"; 
        int hashLength = 32;
        assertEquals("$argon2i$v=19$m=65535,t=2,p=1$SGtZRGRDN0Z2VkVsUGRxOA$TsaPqDZd+YDYQKFXvR6SKxKYP3h+XDk+NCHK85/QQYA", Util.hashPassword(input, salt.getBytes(), hashLength).getEncoded());
        // If this passes, the 2 other hash methods should also pass.

    }

    @Test // Test passed
    void verificationTest() {
        String password = "Never Gonna Give You Up, Never Gonna Let You Down";
        String hash = Util.hashPassword(password);
        assertTrue(Util.verifyPassword(password, hash));
        assertFalse(Util.verifyPassword("Never Gonna Make You Cry, Never Gonna Say Goodbye", hash));
    }

    @Test // Test passed
    void encryptionTest() {
        String password = "Never Gonna Give You Up, Never Gonna Let You Down";
        AsymmetricCipherKeyPair keyPair = Util.generateX25519KeyPair();
        String privateKey = Util.bytesToBase64(Util.getX25519PrivateKey(keyPair));
        String encrypted = Util.encryptPrivateKey(password, privateKey);
        String decrypted = Util.decryptPrivateKey(password, encrypted);
        assertEquals(privateKey, decrypted);
    }

    @Test // Test passed
    void x25519KeyPairGenerationTest() {
        AsymmetricCipherKeyPair X25519KeyPair = Util.generateX25519KeyPair();

        byte[] privateKey = Util.getX25519PrivateKey(X25519KeyPair);
        byte[] publicKey = Util.getX25519PublicKey(X25519KeyPair);

        System.out.println("Private Key: " +  Util.bytesToBase64(privateKey));
        System.out.println("Private Key Bytes length: " + privateKey.length);
        System.out.println("Public Key: " +  Util.bytesToBase64(publicKey));
        System.out.println("Public Key Bytes length: " + publicKey.length);
    }

    @Test // Test passed
    void X25519SharedKeyGenerationTest() {
        // 1. Generte Bob's key pair
        AsymmetricCipherKeyPair bobX25519KeyPair = Util.generateX25519KeyPair();

        byte[] bobPrivateKey = Util.getX25519PrivateKey(bobX25519KeyPair);
        byte[] bobPublicKey = Util.getX25519PublicKey(bobX25519KeyPair);

        // 2. Generate ALice's key pair
        AsymmetricCipherKeyPair aliceX25519KeyPair = Util.generateX25519KeyPair();

        byte[] alicePrivateKey = Util.getX25519PrivateKey(aliceX25519KeyPair);
        byte[] alicePublicKey = Util.getX25519PublicKey(aliceX25519KeyPair);

        // 3. Alice computes the shared secret
        String aliceSharedSecret = Util.bytesToBase64(Util.generateX25519SharedSecret(alicePrivateKey, bobPublicKey));

        // 4. Bob computes the shared secret
        String bobSharedSecret = Util.bytesToBase64(Util.generateX25519SharedSecret(bobPrivateKey, alicePublicKey));

        // 5. Verify that the shared secrets are the same
        assertEquals(aliceSharedSecret, bobSharedSecret);
    }


    // @Test // passed
    // void Ed25519KeyPairGenerationTest() {
    //     try {
    //         System.out.println("Generating Ed25519 key pair...");
    //
    //         // Call the function to generate the key pair
    //         AsymmetricCipherKeyPair ed25519KeyPair = Util.generateEd25519KeyPair();
    //
    //         // Extract the private and public key parameters from the generated pair.
    //         Ed25519PrivateKeyParameters privateKey = (Ed25519PrivateKeyParameters) ed25519KeyPair.getPrivate();
    //         Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) ed25519KeyPair.getPublic();
    //
    //         // Print the generated keys in hexadecimal format for easy viewing.
    //         // In a real application, you would handle these keys securely (e.g., store them).
    //         System.out.println("Ed25519 Private Key (Hex): " + Hex.toHexString(privateKey.getEncoded()));
    //         System.out.println("Ed25519 Public Key (Hex): " + Hex.toHexString(publicKey.getEncoded()));
    //
    //         System.out.println("\nEd25519 Key Pair Generation Complete.");
    //
    //     } catch (Exception e) {
    //         System.err.println("An error occurred during key generation: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }

    @Test
    void Ed25519toX25519KeyPairTest() {
        try {
            System.out.println("--- Generating Bob's Ed25519 Key Pair ---");

            // Call the function to generate the Ed25519 key pair
            AsymmetricCipherKeyPair BobEd25519KeyPair = Util.generateEd25519KeyPair();

            // Call the function to convert the Ed25519 key pair to Curve25519
            AsymmetricCipherKeyPair BobCurve25519KeyPair = Util.generateX25519KeyPair(Util.getEd25519PrivateKey(BobEd25519KeyPair));

            // Extract and print the Curve25519 private and public keys
            byte[] BobCurvePrivateKey = Util.getX25519PrivateKey(BobCurve25519KeyPair);
            byte[] BobCurvePublicKey = Util.getX25519PublicKey(BobCurve25519KeyPair);

            // Call the function to generate the Ed25519 key pair
            AsymmetricCipherKeyPair AliceEd25519KeyPair = Util.generateEd25519KeyPair();

            // Call the function to convert the Ed25519 key pair to Curve25519
            AsymmetricCipherKeyPair AliceCurve25519KeyPair = Util.generateX25519KeyPair(Util.getEd25519PrivateKey(AliceEd25519KeyPair));

            // Extract and print the Curve25519 private and public keys
            byte[] AliceCurvePrivateKey = Util.getX25519PrivateKey(AliceCurve25519KeyPair);
            byte[] AliceCurvePublicKey = Util.getX25519PublicKey(AliceCurve25519KeyPair);

            // 3. Alice computes the shared secret
            String aliceSharedSecret = Util.bytesToBase64(Util.generateX25519SharedSecret(AliceCurvePrivateKey,BobCurvePublicKey));

            // 4. Bob computes the shared secret
            String bobSharedSecret = Util.bytesToBase64(Util.generateX25519SharedSecret(BobCurvePrivateKey,AliceCurvePublicKey));

            // 5. Verify that the shared secrets are the same
            assertEquals(aliceSharedSecret, bobSharedSecret);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static byte[] deriveAesGcmKeyAndIvWithHKDF(byte[] sharedSecret) {
        if (sharedSecret == null || sharedSecret.length == 0) {
            System.err.println("Shared secret cannot be null or empty for HKDF derivation.");
            return null;
        }

        // We need 32 bytes for the AES-256 key and 12 bytes for the AES-GCM IV.
        // Total output length required is 32 + 12 = 44 bytes.
        int outputLength = 32 + 12;

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

        System.out.println("Derived " + outputLength + " bytes using HKDF-SHA256.");
        System.out.println("Derived Bytes (Hex): " + Hex.toHexString(derivedBytes));

        return derivedBytes;
    }

    // @Test 
    // void SymmetricKeyGenerationTest() {
    //     try {
    //         System.out.println("--- Generating Ed25519 Key Pair ---");
    //
    //         // Call the function to generate the Ed25519 key pair
    //         AsymmetricCipherKeyPair ed25519KeyPair = Util.generateEd25519KeyPair();
    //
    //         // Extract and print the Ed25519 private and public keys
    //         Ed25519PrivateKeyParameters edPrivateKey = (Ed25519PrivateKeyParameters) ed25519KeyPair.getPrivate();
    //         Ed25519PublicKeyParameters edPublicKey = (Ed25519PublicKeyParameters) ed25519KeyPair.getPublic();
    //         System.out.println("Ed25519 Private Key (Hex): " + Hex.toHexString(edPrivateKey.getEncoded()));
    //         System.out.println("Ed25519 Public Key (Hex): " + Hex.toHexString(edPublicKey.getEncoded()));
    //
    //         System.out.println("\n--- Converting Ed25519 to Curve25519 Key Pair ---");
    //
    //         // Call the function to convert the Ed25519 key pair to Curve25519
    //         AsymmetricCipherKeyPair curve25519KeyPair = Util.generateX25519KeyPair(ed25519KeyPair);
    //
    //         // Extract and print the Curve25519 private and public keys
    //         X25519PrivateKeyParameters curvePrivateKey = (X25519PrivateKeyParameters) curve25519KeyPair.getPrivate();
    //         X25519PublicKeyParameters curvePublicKey = (X25519PublicKeyParameters) curve25519KeyPair.getPublic();
    //         System.out.println("Curve25519 Private Key (Hex): " + Hex.toHexString(curvePrivateKey.getEncoded()));
    //         System.out.println("Curve25519 Public Key (Hex): " + Hex.toHexString(curvePublicKey.getEncoded()));
    //
    //         System.out.println("\nKey Generation and Conversion Complete.");
    //
    //     } catch (Exception e) {
    //         System.err.println("An error occurred: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }


}
