package org.pexamax.acheron.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
class Conversation {
    // private static int lastConversationID = 0;  // Replace with value from DB

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationID; // Auto-generated and auto-incremented by JPA

    private boolean hidden;
    private UUID user1ID;
    private UUID user2ID;
    private String user1EncSharedSecret;
    private String user2EncSharedSecret;
    private Instant createdTimestamp;

    public Conversation(UUID user1ID, UUID user2ID, String user1EncSharedSecret, String user2EncSharedSecret) {
        // conversationID = lastConversationID++;
        // this.hidden = false;
        // this.user1ID = user1ID;
        // this.user2ID = user2ID;
        // this.user1EncSharedSecret = user1EncSharedSecret;
        // this.user2EncSharedSecret = user2EncSharedSecret;
        // this.createdTimestamp = Instant.now();
        // conversationID(lastConversationID++);
        this.hidden(false);
        this.user1ID(user1ID);
        this.user2ID(user2ID);
        this.user1EncSharedSecret(user1EncSharedSecret);
        this.user2EncSharedSecret(user2EncSharedSecret);
        this.createdTimestamp(Instant.now());
    }

    // Getters & Setters
    public Long conversationID() { return this.conversationID; }
    public void conversationID(Long conversationID) { this.conversationID = conversationID; }

    public boolean hidden() { return this.hidden; }
    public void hidden(boolean hidden) { this.hidden = hidden; }

    public UUID user1ID() { return this.user1ID; }
    public void user1ID(UUID user1ID) { this.user1ID = user1ID; }

    public UUID user2ID() { return this.user2ID; }
    public void user2ID(UUID user2ID) { this.user2ID = user2ID; }

    public String user1EncSharedSecret() { return this.user1EncSharedSecret; }
    public void user1EncSharedSecret(String user1EncSharedSecret) { this.user1EncSharedSecret = user1EncSharedSecret; }

    public String user2EncSharedSecret() { return this.user2EncSharedSecret; }
    public void user2EncSharedSecret(String user2EncSharedSecret) { this.user2EncSharedSecret = user2EncSharedSecret; }

    public Instant createdTimestamp() { return this.createdTimestamp; }
    public void createdTimestamp(Instant createdTimestamp) { this.createdTimestamp = createdTimestamp; }
}
