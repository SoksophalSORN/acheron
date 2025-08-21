package org.pexamax.acheron.model;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.pexamax.acheron.Util;

import java.util.ArrayList;

import java.sql.Blob;
import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcTemplate;

public class User implements Persistable {

    private long userID;

    private String username;
    private String email;
    private String passwordHash;
    private boolean emailVerified = false;
    private byte[] EdPublicKey;
    private byte[] EdPrivateKey;
    private byte[] XPublicKey;
    private byte[] XPrivateKey;

    // Retrieve user data based on userID
    public User(String username, String email, String passwordHash, boolean emailVerified, byte[] EdPublicKey, byte[] EdPrivateKey, byte[] XPublicKey, byte[] XPrivateKey) {
        setUsername(username);
        setEmail(email);
        setPasswordHash(passwordHash);
        setEmailVerified(emailVerified);
        setEdPublicKey(EdPublicKey);
        setEdPrivateKey(EdPrivateKey);
        setXPublicKey(XPublicKey);
        setXPrivateKey(XPrivateKey);
    }

    // For registration
    public User(String username, String email, String password) {
        this.username = username;
        this.passwordHash = Util.hashPassword(password);
        this.email = email;
        AsymmetricCipherKeyPair Ed25519KeyPair = Util.generateEd25519KeyPair();
        this.EdPublicKey = Util.getEd25519PublicKey(Ed25519KeyPair);
        this.EdPrivateKey = Util.getEd25519PrivateKey(Ed25519KeyPair);
        AsymmetricCipherKeyPair X25519KeyPair = Util.generateX25519KeyPair(this.EdPrivateKey);
        this.XPublicKey = Util.getX25519PublicKey(X25519KeyPair);
        this.XPrivateKey = Util.getX25519PrivateKey(X25519KeyPair);
    }

    private void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    private void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }

    private void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
        this.passwordHash = passwordHash;
    }

    private void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    private void setEdPublicKey(byte[] edPublicKey) {
        if (edPublicKey == null || edPublicKey.length == 0) {
            throw new IllegalArgumentException("ED Public Key cannot be null or empty");
        }
        this.EdPublicKey = edPublicKey;
    }

    private void setEdPrivateKey(byte[] edPrivateKey) {
        if (edPrivateKey == null || edPrivateKey.length == 0) {
            throw new IllegalArgumentException("ED Private Key cannot be null or empty");
        }
        this.EdPrivateKey = edPrivateKey;
    }

    private void setXPublicKey(byte[] xPublicKey) {
        if (xPublicKey == null || xPublicKey.length == 0) {
            throw new IllegalArgumentException("X Public Key cannot be null or empty");
        }
        this.XPublicKey = xPublicKey;
    }

    private void setXPrivateKey(byte[] xPrivateKey) {
        if (xPrivateKey == null || xPrivateKey.length == 0) {
            throw new IllegalArgumentException("X Private Key cannot be null or empty");
        }
        this.XPrivateKey = xPrivateKey;
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

    public static User login(String email, String password) {
        // Retrieve user from database by email
        // if (user record exists) {
        // if (Util.verifyPassword(password, passwordHash)) {
        // return user object;
        // }
        // }
        return null;
    }

    // Method for registering a new user
    public static User register(String username, String email, String password, JdbcTemplate template) {
        // Handle registration process with db
        // Check if username or email already exists in the database
        // if (username or email exists) { return null; }
        // Generate userID and public/private keys
        // Hash the password
        // Save user to the database
        // Return new User object
        String sql = "SELECT COUNT(1) FROM users WHERE username = ? OR email = ?";
        long count = template.queryForObject(sql, Integer.class, username, email);
        if (count > 0) {
            throw new IllegalArgumentException("Username or email already exists");
        }
        return new User(username, email, password);
    }

    public boolean verifyPassword(String password) {
        return Util.verifyPassword(password, this.passwordHash);
    }

    public byte[] getUserEdPublicKey(long userID) {
        // Retrieve public key from database by userID
        byte[] publicKey = Util.utf8ToBytes("publicKey"); // placeholder
        return publicKey;
    }

    public byte[] getUserXPublicKey(long userID) {
        // Retrieve public key from database by userID
        byte[] publicKey = Util.utf8ToBytes("publicKey"); // placeholder
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
