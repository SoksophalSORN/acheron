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
        userID = lastUserID++;
        this.username = username;
        this.email = email;
        this.emailVerified = false;
        this.passwordHash = passwordHash;
        this.publicKey = publicKey;
        this.encPrivateKey = encPrivateKey;
    }
}
