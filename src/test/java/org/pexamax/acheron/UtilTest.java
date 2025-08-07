// Package and Imports
// Test command: ./mvnw test -Dtest=UtilTest

package org.pexamax.acheron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// import org.pexamax.acheron.Util;

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

        String bobPrivateKey = Util.bytesToBase64(Util.getX25519PrivateKey(bobX25519KeyPair));
        String bobPublicKey = Util.bytesToBase64(Util.getX25519PublicKey(bobX25519KeyPair));

        // 2. Generate ALice's key pair
        AsymmetricCipherKeyPair aliceX25519KeyPair = Util.generateX25519KeyPair();

        String alicePrivateKey = Util.bytesToBase64(Util.getX25519PrivateKey(aliceX25519KeyPair));
        String alicePublicKey = Util.bytesToBase64(Util.getX25519PublicKey(aliceX25519KeyPair));

        // 3. Alice computes the shared secret
        String aliceSharedSecret = Util.bytesToBase64(Util.generateX25519SharedSecret(alicePrivateKey, bobPublicKey));

        // 4. Bob computes the shared secret
        String bobSharedSecret = Util.bytesToBase64(Util.generateX25519SharedSecret(bobPrivateKey, alicePublicKey));

        // 5. Verify that the shared secrets are the same
        assertEquals(aliceSharedSecret, bobSharedSecret);
    }
}
