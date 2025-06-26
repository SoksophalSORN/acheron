package org.pexamax.acheron.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class User { // Made public because it is used in other packages
    // private static int lastUserID = 0;  // Replace with value from DB

    @Id
    @GeneratedValue
    private UUID userID; // Auto-generated UUID for user ID

    private String username;
    private String email;
    private boolean emailVerified;
    private String passwordHash;
    private String publicKey;
    private String encPrivateKey;

    // Default constructor - required by JPA
    public User() {}

    public User(String username, String email, String passwordHash, String publicKey, String encPrivateKey) {
        // userID = lastUserID++;
        // this.username = username;
        // this.email = email;
        // this.emailVerified = false;
        // this.passwordHash = passwordHash;
        // this.publicKey = publicKey;
        // this.encPrivateKey = encPrivateKey;
        // this.userID(lastUserID++);
        this.setUsername(username);
        this.setEmail(email);
        this.setEmailVerified(false);
        this.setPasswordHash(passwordHash);
        this.setPublicKey(publicKey);
        this.setEncPrivateKey(encPrivateKey);
    }
    
    public UUID getUserID() { return userID; }
    public void setUserID(UUID userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

    public String getEncPrivateKey() { return encPrivateKey; }
    public void setEncPrivateKey(String encPrivateKey) { this.encPrivateKey = encPrivateKey; }
}
