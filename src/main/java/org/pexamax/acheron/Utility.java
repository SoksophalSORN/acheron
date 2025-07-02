// Package and Imports
package org.pexamax.acheron;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Utility {
    // hashPassword method
    // encrypt/decrypt private key method
    //      Derive keys from password before encrypt/decrypt
    //      AES-GCM encryt/decrypt for private key
    private static final Argon2 argon2 = Argon2Factory.create();


    public static String hashPassword(String password) {

        return password;
    }
}

