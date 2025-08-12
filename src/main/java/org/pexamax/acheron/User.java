package org.pexamax.acheron;

import org.pexamax.acheron.Conversation;
import org.pexamax.acheron.Util;

import java.util.ArrayList;

public class User implements Persistable {

    private long userID; // 64 hexadecimal characters

    private String username;
    private String email;
    private String passwordHash;
    private boolean emailVerified = false;
    private byte[] publicKey;
    private byte[] privateKey;

    // Retrieve user data based on userID
    public User(long userID) {
        this.userID = userID;
        // Load user data from the database
        load();
        // User(all the loaded fields);
    }

    // For registration
    public User(String username, String email, String password) {
        // this.userID = Util.generateHexID(32);
        this.username = username;
        this.passwordHash = Util.hashPassword(password);
        this.email = email;
        this.publicKey = Util.utf8ToBytes("publicKey"); // generate public key
        this.privateKey = Util.utf8ToBytes("PrivateKey"); // generated private key
    }

    public long getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public boolean changeUsername(String username) {
        // if (username exists in the database) { return false; }
        // update username in the database
        // this.username = username;
        return true;
        // }
        // return false;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean changeEmail(String email) {
        // if (email exists in the database) { return false; }
        // update email in the database
        // this.email = email;
        return true;
        // }
        // return false;
    }

    public static User login(String username, String password) {
        // Retrieve user from database by username
        // if (user record exists) {
        // if (Utility.verifyPassword(password, passwordHash)) {
        // construct user object
        // return user object;
        // }
        // }
        return null;
    }

    // Method for registering a new user
    public static User register(String username, String email, String password) {
        // Handle registration process with db
        // Check if username or email already exists in the database
        // if (username or email exists) { return null; }
        // Generate userID and public/private keys
        // Hash the password
        // Save user to the database
        // Return new User object
        return new User(username, email, password);
    }

    public boolean verifyPassword(String password) {
        return Util.verifyPassword(password, this.passwordHash);
    }

    public String getUserPublicKey(long userID) {
        // Retrieve public key from database by userID
        String publicKey = "publicKey"; // placeholder
        return publicKey;
    }

    @Override
    public void load() {
        // Load user data from the database
        // This method should retrieve the user data based on userID and populate the
        // fields to construct a user object
        // this.userID = userID;
        // this.username = username;
        // this.passwordHash = passwordHash;
        // this.email = email;
        // this.emailVerified = emailVerified;
        // this.publicKey = publicKey;
        // this.privateKey = encPrivateKey; // decrypt and store
    }

    @Override
    public void save() {
        // Save user data to the database
        // This method should save the current state of the user object to the database
    }
}
