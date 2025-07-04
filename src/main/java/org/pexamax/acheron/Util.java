// Package and Imports
package org.pexamax.acheron;

// Argon2id
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.HashResult;

public class Util {
    // encrypt/decrypt private key method
    //      Derive keys from password before encrypt/decrypt
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
    public static HashResult hashPassowrdResult(String password) {
        int iterations = 2;
        int memory = 65535; // 64 MB
        int parallelism = 1; // Single-threaded
        int saltLength = 12;
        int hashLength = 44; // 32 bytes for AES-GCM secret key + 12 bytes for IV
        de.mkammerer.argon2.Argon2Version version = de.mkammerer.argon2.Argon2Version.V13; // Use Argon2 v1.3; has 2 values: V10 and V13
        byte[] salt = argon2Advanced.generateSalt(saltLength); // Generate a random salt of 12 bytes
        // return both raw byte and the encoded representation
        return argon2Advanced.hashAdvanced(iterations, memory, parallelism, password.getBytes(java.nio.charset.StandardCharsets.UTF_8), salt, hashLength, version);
    }

    // Generate secret key and IV from password
    public static byte[][] generateSecretAndIV(String password) {
        HashResult passwordHashResult = hashPassowrdResult(password);
        byte[] rawPassowrdHash = passwordHashResult.getRaw();
        byte[] secretKey = new byte[32]; // 32 bytes for AES-GCM secret key
        System.arraycopy(rawPassowrdHash, 0, secretKey, 0, secretKey.length); // Copy first 32 bytes for secret key from 
        byte[] IV = new byte[12]; // 12 bytes for IV
        System.arraycopy(rawPassowrdHash, secretKey.length, IV, 0, IV.length); // Copy next 12 bytes for IV from the raw password hash
        return new byte[][] { secretKey, IV }; // return both secret key and IV
    }
    
    public static boolean verifyPassword(String password, String hash) {
        // argon2.verify(String hash, char[] password)
        // hash: the hashed password to verify against
        // password: the plain text password to verify
        return argon2.verify(hash, password.toCharArray());
    }
}

