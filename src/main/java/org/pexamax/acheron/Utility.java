// Package and Imports
package org.pexamax.acheron;

// Argon2id
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import java.security.SecureRandom;

public class Utility {
    // encrypt/decrypt private key method
    // Derive keys from password before encrypt/decrypt
    // AES-GCM encryt/decrypt for private key

    private static final SecureRandom random = new SecureRandom();
    private static final Argon2 argon2 = Argon2Factory.create();

    // Take plain password, return hash
    public static String hashPassword(String password) {
        // argon2.hash(int iterations, int memory, int parallelism, char[] password)
        // iterations: number of iterations (or time cost). Higher means more hashing
        // time and better resistance to brute-force attacks.
        // memory: memory usage in kilobytes (here, 65536 KB = 64 MB). More memory makes
        // it harder for attackers to use GPUs or ASICs.
        // parallelism: number of parallel threads or compute lanes used. 1 means
        // single-threaded hashing.
        // password: nah, you know what this is.
        return argon2.hash(2, 65536, 1, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hash) {
        // argon2.verify(String hash, char[] password)
        // hash: the hashed password to verify against
        // password: the plain text password to verify
        return argon2.verify(hash, password.toCharArray());
    }

    // Salt generation method;
    public static byte[] generateSalt(int length) {
        byte salt[] = new byte[length];
        random.nextBytes(salt);
        return salt;
    }

    public static String generateHexID(unsigned length) {
        String id = "generate the id with the specified length";
        return id;
    }

    public static SecretKey generateSecretAndIV(String password) {
        // Use password and randomly-generated salt
        // use argon2 to hash
        // return pseudorandom key
        // slice the key; the first 30 bytes ish is the secret key, the rest is the IV
    }
}
