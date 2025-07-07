// Package and Imports
package org.pexamax.acheron;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

// Argon2id - https://javadoc.io/static/de.mkammerer/argon2-jvm/2.8/de/mkammerer/argon2/package-summary.html
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.HashResult;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Util {
    // encrypt/decrypt private key method
    //      AES-GCM encryt/decrypt for private key

    private static final Argon2 argon2 = Argon2Factory.create();
    private static final Argon2Advanced argon2Advanced = Argon2Factory.createAdvanced();

    // Take plain password, return hash
    public static String hashPassword(String password) {
        // argon2.hash(int iterations, int memory, int parallelism, char[] password)
        // iterations: number of iterations (or time cost). Higher means more hashing time and better resistance to brute-force attacks.
        // memory: memory usage in kilobytes (here, 65536 KB = 64 MB). More memory makes it harder for attackers to use GPUs or ASICs.
        // parallelism: number of parallel threads or compute lanes used. 1 means single-threaded hashing.
        // password: nah, you know what this is.
        return argon2.hash(2, 65536, 1, password.toCharArray());
    }

    // Hash password with Argon2 and return both raw byte and encoded representation
    public static HashResult hashPassowrd(String password, int hashLength) {
        int iterations = 2;
        int memory = 65535; // 64 MB
        int parallelism = 1; // Single-threaded
        int saltLength = 12;
        de.mkammerer.argon2.Argon2Version version = de.mkammerer.argon2.Argon2Version.V13; // Use Argon2 v1.3; has 2 values: V10 and V13
        byte[] salt = argon2Advanced.generateSalt(saltLength); // Generate a random salt of 12 bytes
        // return both raw byte and the encoded representation
        return argon2Advanced.hashAdvanced(iterations, memory, parallelism, password.getBytes(StandardCharsets.UTF_8), salt, hashLength, version);
    }

    public static boolean verifyPassword(String password, String hash) {
        // argon2.verify(String hash, char[] password)
        // hash: the hashed password to verify againstjavax.crypto.spec
        // password: the plain text password to verify
        return argon2.verify(hash, password.toCharArray());
    }


    // Generate secret key and IV from password
    public static String encryptPrivateKey(String password, String privateKey) throws Exception {
        int iterations = 2;
        int memory = 65535; // 64 MB
        int parallelism = 1; // Single-threaded
        int secretKeyLength = 16; // 16 bytes for secret key
        int IVLength = 12; // 12 bytes for IV
        int hashLength = secretKeyLength + IVLength; // 16 bytes for secret key
        de.mkammerer.argon2.Argon2Version version = de.mkammerer.argon2.Argon2Version.V13; // Use Argon2 v1.3; has 2 values: V10 and V13
        byte[] salt = argon2Advanced.generateSalt(16); // 16-byte long salt for Argon2

        HashResult passwordHashResult = argon2Advanced.hashAdvanced(iterations, memory, parallelism, password.getBytes(java.nio.charset.StandardCharsets.UTF_8), salt, hashLength, version);
        byte[] rawPassowrdHash = passwordHashResult.getRaw();

        byte[] secretKey = new byte[secretKeyLength]; // 32 bytes for AES-GCM secret key
        System.arraycopy(rawPassowrdHash, 0, secretKey, 0, secretKey.length); // Copy first 32 bytes for secret key from 

        byte[] IV = new byte[IVLength]; // 12 bytes for IV
        System.arraycopy(rawPassowrdHash, secretKey.length, IV, 0, IV.length); // Copy next 12 bytes for IV from the raw password hash

        // Initialize AES-GCM cipher
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        GCMParameterSpec IVSpec = new GCMParameterSpec(128, IV); // 128-bit tag (cryptographic checksum) length
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IVSpec);

        // Encrypt the private key
        byte[] encryptedPrivateKey = cipher.doFinal(privateKey.getBytes(StandardCharsets.UTF_8));

        // Combine the encrypted private key and salt into a single byte array
        byte[] combinedCipherAndSalt = new byte[cipher.getOutputSize(encryptedPrivateKey.length) + salt.length];
        System.arraycopy(encryptedPrivateKey, 0, combinedCipherAndSalt, 0, encryptedPrivateKey.length);
        System.arraycopy(salt, 0, combinedCipherAndSalt, encryptedPrivateKey.length, salt.length);
        
        // Return the Base64-encoded string of the combined cipher and salt
        return Base64.getEncoder().encodeToString(combinedCipherAndSalt);
    }

    public static <type> AsymEncrypt();
    public static <type> AsymDecrypt();

    public static <type> SymEncrypt();
    public static <type> SymDecrypt();
}
