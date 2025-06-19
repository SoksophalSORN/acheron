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
    private Instant lastMessageSentTimestamp;
    private UUID blockerID;

    public Conversation(UUID user1ID, UUID user2ID, String user1EncSharedSecret, String user2EncSharedSecret) {
        // conversationID = lastConversationID++;
        // this.hidden = false;
        // this.user1ID = user1ID;
        // this.user2ID = user2ID;
        // this.user1EncSharedSecret = user1EncSharedSecret;
        // this.user2EncSharedSecret = user2EncSharedSecret;
        // this.createdTimestamp = Instant.now();
        // conversationID(lastConversationID++);
        this.setHidden(false);
        this.setUser1ID(user1ID);
        this.setUser2ID(user2ID);
        this.setUser1EncSharedSecret(user1EncSharedSecret);
        this.setUser2EncSharedSecret(user2EncSharedSecret);
        this.setCreatedTimestamp(Instant.now());
    }

    // Getters & Setters
    public Long getConversationID() { return this.conversationID; }
    public void setConversationID(Long conversationID) { this.conversationID = conversationID; }

    public boolean getHidden() { return this.hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }

    public UUID getUser1ID() { return this.user1ID; } public void setUser1ID(UUID user1ID) { this.user1ID = user1ID; }

    public UUID getUser2ID() { return this.user2ID; }
    public void setUser2ID(UUID user2ID) { this.user2ID = user2ID; }

    public String getUser1EncSharedSecret() { return this.user1EncSharedSecret; }
    public void setUser1EncSharedSecret(String user1EncSharedSecret) { this.user1EncSharedSecret = user1EncSharedSecret; }

    public String getUser2EncSharedSecret() { return this.user2EncSharedSecret; }
    public void setUser2EncSharedSecret(String user2EncSharedSecret) { this.user2EncSharedSecret = user2EncSharedSecret; }

    public Instant getCreatedTimestamp() { return this.createdTimestamp; }
    public void setCreatedTimestamp(Instant createdTimestamp) { this.createdTimestamp = createdTimestamp; }

    public Instant getLastMessageSentTimestamp() { return this.lastMessageSentTimestamp; }
    public void setLastMessageSentTimestamp(Instant lastMessageSentTimestamp) { this.lastMessageSentTimestamp = lastMessageSentTimestamp; }

    public UUID getBlockerID() { return this.blockerID; }
    public void setBlockerID(UUID blockerID) { this.blockerID = blockerID; }
}
