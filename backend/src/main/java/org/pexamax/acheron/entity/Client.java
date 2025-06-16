package org.pexamax.acheron.entity;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
class Client {
    // private static int lastclientID = 0;  // Replace with value from DB

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long clientID; // Auto-generated and auto-incremented by JPA

    private String sessionToken;
    private String clientToken;
    private Instant sessionTokenExpiry;
    private Instant clientTokenExpiry;

    public Client(String sessionToken, String clientToken, Instant sessionTokenExpiry, Instant clientTokenExpiry) {
        // clientID = lastclientID++;
        // this.sessionToken = sessionToken;
        // this.clientToken = clientToken;
        // this.sessionTokenExpiry = sessionTokenExpiry;
        // this.clientTokenExpiry = clientTokenExpiry;
        // this.clientID(lastclientID++);
        this.sessionToken(sessionToken);
        this.clientToken(clientToken);
        this.sessionTokenExpiry(sessionTokenExpiry);
        this.clientTokenExpiry(clientTokenExpiry);
    }
    
    // Getters & Setters
    public String sessionToken() { return this.sessionToken; }
    public void sessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public Long clientID() { return this.clientID; }
    public void clientID(Long clientID) { this.clientID = clientID; }

    public String clientToken() { return this.clientToken; }
    public void clientToken(String clientToken) { this.clientToken = clientToken; }

    public Instant sessionTokenExpiry() { return this.sessionTokenExpiry; }
    public void sessionTokenExpiry(Instant sessionTokenExpiry) { this.sessionTokenExpiry = sessionTokenExpiry; }

    public Instant clientTokenExpiry() { return this.clientTokenExpiry; }
    public void clientTokenExpiry(Instant clientTokenExpiry) { this.clientTokenExpiry = clientTokenExpiry; }

}
