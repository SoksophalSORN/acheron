package org.pexamax.acheron.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String username;
    private String email;
    private boolean emailVerified;
    private String passwordHash;
    private String publicKey;
    private String encPrivateKey;

    // Default constructor - required by JPA
    public User() {}

    public User(String username, String email, String passwordHash, String publicKey, String encPrivateKey) {
        setUsername(username);
        setEmail(email);
        setEmailVerified(false);
        setPasswordHash(passwordHash);
        setPublicKey(publicKey);
        setEncPrivateKey(encPrivateKey);
    }
    
    public Long getUserID() { return userID; }
    public void setUserID(Long id) { userID = id; }

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
