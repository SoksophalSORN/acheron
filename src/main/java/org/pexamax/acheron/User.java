package org.pexamax.acheron;

import org.pexamax.acheron.Conversation;
import org.pexamax.acheron.Utility;

import java.util.ArrayList;

public class User {

    private String userID; // 64 hexadecimal characters

    private String username;
    private String email;
    private String passwordHash;
    private boolean emailVerified = false;
    private String publicKey;
    private String privateKey;

    // To keep track of all the conversations user has
    private ArrayList<Conversation> conversations = new ArrayList<Conversation>();
    private ArrayList<Conversation> hiddenConversations = new ArrayList<Conversation>();

    // For login
    public User(String userID, String username, String passwordHash, String email, boolean emailVerified,
            String password, String publicKey, String encPrivateKey, int message_destruct_timer) {
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
        this.userID = Utility.generateHexID(32);
        this.username = username;
        this.passwordHash = Utility.hashPassword(password);
        this.email = email;
        this.publicKey = "publicKey"; // generate public key
        this.privateKey = "PrivateKey"; // generated private key
    }

    public String getUserID() {
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

    public boolean verifyPassword(String password) {
        return Utility.verifyPassword(password, this.passwordHash);
    }

    public String getUserPublicKey(String userID) {
        // Retrieve public key from database by userID
        String publicKey = "publicKey"; // placeholder
        return publicKey;
    }

    public void retrieveConversations() {
        // Retrieve 10 conversations at a time based on the userID
        // Then add them to their respective collection whether they are hidden or not
    }

    public void listConversations(boolean hidden) {
        // List all visible/hidden conversations for the user
    }

    public void selectConversation(int index, boolean hidden) {
        // Select the conversation at the specified index
        // Display the messages in the conversation
    }
}
