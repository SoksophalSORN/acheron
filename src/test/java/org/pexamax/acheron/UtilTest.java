// Package and Imports
// Test command: ./mvnw test -Dtest=UtilTest

package org.pexamax.acheron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;



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
        byte[] privateKey = Util.getX25519PrivateKey(keyPair);
        byte[] encrypted = Util.encryptPrivateKey(password, privateKey);
        byte[] decrypted = Util.decryptPrivateKey(password, encrypted);
        assertEquals(Util.bytesToBase64(privateKey), Util.bytesToBase64(decrypted));
    }

    // @Test // Test passed
    // void x25519KeyPairGenerationTest() {
    //     AsymmetricCipherKeyPair X25519KeyPair = Util.generateX25519KeyPair();
    //
    //     byte[] privateKey = Util.getX25519PrivateKey(X25519KeyPair);
    //     byte[] publicKey = Util.getX25519PublicKey(X25519KeyPair);
    //
    //     System.out.println("Private Key: " +  Util.bytesToBase64(privateKey));
    //     System.out.println("Private Key Bytes length: " + privateKey.length);
    //     System.out.println("Public Key: " +  Util.bytesToBase64(publicKey));
    //     System.out.println("Public Key Bytes length: " + publicKey.length);
    // }

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

    @Test // passed
    List<byte[]> Ed25519toX25519KeyPairTest() {
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
            byte[] aliceSharedSecret = Util.generateX25519SharedSecret(AliceCurvePrivateKey,BobCurvePublicKey);

            // 4. Bob computes the shared secret
            byte[] bobSharedSecret = Util.generateX25519SharedSecret(BobCurvePrivateKey,AliceCurvePublicKey);

            // 5. Verify that the shared secrets are the same
            assertEquals(Util.bytesToBase64(aliceSharedSecret), Util.bytesToBase64(bobSharedSecret));

            return List.of(aliceSharedSecret, Util.getEd25519PrivateKey(AliceEd25519KeyPair), Util.getEd25519PublicKey(AliceEd25519KeyPair),bobSharedSecret, Util.getEd25519PrivateKey(BobEd25519KeyPair), Util.getEd25519PublicKey(BobEd25519KeyPair));

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Test // passed
    List<byte[]> SymmetricKeyGenerationTest() {
        try {
            List<byte[]> keys = Ed25519toX25519KeyPairTest();
            byte[] aliceSharedSecret = keys.get(0);
            byte[] aliceSymmetricKey = Util.generateSymmetricKey(aliceSharedSecret);

            byte[] bobSharedSecret = keys.get(3);
            byte[] bobSymmetricKey = Util.generateSymmetricKey(bobSharedSecret);

            assertEquals(Util.bytesToBase64(aliceSymmetricKey), Util.bytesToBase64(bobSymmetricKey));
            return List.of(aliceSymmetricKey, bobSymmetricKey);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Test // passed
    void symmetricEncryptionTest() {
        List<byte[]> keys = SymmetricKeyGenerationTest();
        byte[] aliceSymmetricKey = keys.get(0);
        byte[] bobSymmetricKey = keys.get(1);
        String message = "Never Gonna Give You Up, Never Gonna Let You Down";
        byte[] encryptedMessage = Util.symEncrypt(aliceSymmetricKey, message.getBytes());
        byte[] decryptedMessage = Util.symDecrypt(bobSymmetricKey, encryptedMessage);
        System.out.println("Original Message: " + message);
        System.out.println("Decrypted Message: " + Util.bytesToUTF8(decryptedMessage));
        assertEquals(message, Util.bytesToUTF8(decryptedMessage));
    }


}
