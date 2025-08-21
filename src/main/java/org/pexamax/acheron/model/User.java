package org.pexamax.acheron.model;

import org.pexamax.acheron.Util;
import org.pexamax.acheron.dao.UserDao;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import org.springframework.jdbc.core.JdbcTemplate;


public class User implements Persistable {

    private long userID;

    private String username;
    private String email;
    private boolean emailVerified = false;
    private byte[] EdPublicKey;
    private byte[] EdPrivateKey;
    private byte[] XPublicKey;
    private byte[] XPrivateKey;

    // For retrieving user data
    public User(long userID, String username, String email, String password, boolean emailVerified, 
            byte[] EdPublicKey, byte[] encEdPrivateKey) {
        setUserID(userID);
        setUsername(username);
        setEmail(email);
        setEmailVerified(emailVerified);
        setEdPublicKey(EdPublicKey);
        setEdPrivateKey(Util.decryptPrivateKey(password, EdPrivateKey));
        AsymmetricCipherKeyPair X25519KeyPair = Util.generateX25519KeyPair(this.EdPrivateKey);
        setXPublicKey(Util.getX25519PublicKey(X25519KeyPair));
        setXPrivateKey(Util.getX25519PrivateKey(X25519KeyPair));
    }

    // For registration
    public User(String username, String email, String password) {
        setUsername(username);
        setEmail(email);
        AsymmetricCipherKeyPair Ed25519KeyPair = Util.generateEd25519KeyPair();
        setEdPublicKey(Util.getEd25519PublicKey(Ed25519KeyPair)); ;
        setEdPrivateKey(Util.getEd25519PrivateKey(Ed25519KeyPair));
        AsymmetricCipherKeyPair X25519KeyPair = Util.generateX25519KeyPair(this.EdPrivateKey);
        setXPublicKey(Util.getX25519PublicKey(X25519KeyPair));
        setXPrivateKey(Util.getX25519PrivateKey(X25519KeyPair));
    }

    private void setUserID(long userID) {
        if (userID <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        this.userID = userID;
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

    public static User login(String email, String password, JdbcTemplate template) {
        String passwordHash = Util.hashPassword(password);
        User currentUser = UserDao.login(email, passwordHash, template);
        if (currentUser == null) {
            throw new IllegalArgumentException("Invalid email or password");
        } else return currentUser;
    }

    // Method for registering a new user
    public static User register(String username, String email, String password, JdbcTemplate template) {
        if (UserDao.isExists(username, email, template)) {
            throw new IllegalArgumentException("Username or email already exists.");
        }
        User newUser = UserDao.register(username, email, password, template);
        if (newUser == null) {
            throw new IllegalArgumentException("Registration failed. User already exists or invalid data.");
        } else {
            return newUser;
        }
    }

    public byte[] getEdPublicKey() {
        return this.EdPublicKey;
    }

    public byte[] getEdPrivateKey() {
        return this.EdPrivateKey;
    }

    public byte[] getUserEdPublicKey(long userID) {
        // Retrieve public key from database by userID
        byte[] publicKey = Util.utf8ToBytes("publicKey"); // placeholder
        return publicKey;
    }

    // public byte[] getUserXPublicKey(long userID) {
    //     // Retrieve public key from database by userID
    //     byte[] publicKey = Util.utf8ToBytes("publicKey"); // placeholder
    //     return publicKey;
    // }

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
