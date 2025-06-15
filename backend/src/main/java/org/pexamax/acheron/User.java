package org.pexamax.acheron;

class User {
    private static int lastUserID = 0;  // Replace with value from DB

    private int userID;
    private String username;
    private String email;
    private boolean emailVerified;
    private String passwordHash;
    private String publicKey;
    private String encPrivateKey;

    public User(String username, String email, String passwordHash, String publicKey, String encPrivateKey) {
        // userID = lastUserID++;
        // this.username = username;
        // this.email = email;
        // this.emailVerified = false;
        // this.passwordHash = passwordHash;
        // this.publicKey = publicKey;
        // this.encPrivateKey = encPrivateKey;
        this.userID(lastUserID++);
        this.username(username);
        this.email(email);
        this.emailVerified(false);
        this.passwordHash(passwordHash);
        this.publicKey(publicKey);
        this.encPrivateKey(encPrivateKey);
    }
    
    public int userID() { return userID; }
    public void userID(int userID) { this.userID = userID; }

    public String username() { return username; }
    public void username(String username) { this.username = username; }

    public String email() { return email; }
    public void email(String email) { this.email = email; }

    public boolean emailVerified() { return emailVerified; }
    public void emailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

    public String passwordHash() { return passwordHash; }
    public void passwordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String publicKey() { return publicKey; }
    public void publicKey(String publicKey) { this.publicKey = publicKey; }

    public String encPrivateKey() { return encPrivateKey; }
    public void encPrivateKey(String encPrivateKey) { this.encPrivateKey = encPrivateKey; }
}
