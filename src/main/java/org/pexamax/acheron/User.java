package org.pexamax.acheron;

import org.pexamax.acheron.Utility;

public class User {

    private String userID; // 64 hexadecimal characters

    private String username;
    private String email;
    private String passwordHash;
    private boolean emailVerified = false;
    private String publicKey;
    private String privateKey;

    // For login
    public User(String userID, String username, String passwordHash, String email, boolean emailVerified, String password, String publicKey, String encPrivateKey, int message_destruct_timer) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.emailVerified = emailVerified;
        this.publicKey = publicKey;
        this.privateKey = encPrivateKey; // decrypt and store
    }

    // For registration
    public User(String username, String email, String password) {
        this.userID = generateUserID();
        this.username = username;
        this.passwordHash = Utility.hashPassword(password);
        this.email = email;
        this.publicKey = "publicKey"; // generate public key
        this.privateKey = "PrivateKey"; // generated private key
    }
    
    public String getUserID() { return userID; }

    public String getUsername() { return username; }

    public boolean changeUsername(String username) { 
        // if (username exists in the database) { return false; }
        // update username in the database
        // this.username = username;
        return true;
        // } 
        // return false;
    }

    public String getEmail() { return email; }

    public boolean isEmailVerified() { return emailVerified; }

    public boolean changeEmail(String email) { 
        // if (email exists in the database) { return false; }
        // update email in the database
        // this.email = email;
        return true;
        // } 
        // return false;
    }

    public String getPublicKey() { return publicKey; }

    public String getPrivateKey() { return privateKey; }

    public static User login(String username, String password) {
        // Retrieve user from database by username
        // if (user exists) {
        //    if (Utility.verifyPassword(password, passwordHash)) {
        //        construct user object
        //        return user object;
        //    } 
        // }
        return null;
    }

    public boolean verifyPassword(String password) {
        return Utility.verifyPassword(password, this.passwordHash);
    }

    private String generateUserID() {
        return "uniqueUserID"; // placeholder
    }

    public String getUserPublicKey(String userID) {
        // Retrieve public key from database by userID
        String publicKey = "publicKey"; // placeholder
        return publicKey;
    }
}
