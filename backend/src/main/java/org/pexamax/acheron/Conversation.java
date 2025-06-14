package org.pexamax.acheron;

import java.time.Instant;

class Conversation {
    private static int lastConversationID = 0;  // Replace with value from DB

    private int conversationID;
    private boolean hidden;
    private String user1ID;
    private String user2ID;
    private String user1EncSharedSecret;
    private String user2EncSharedSecret;
    private Instant createdTimestamp;

    public Conversation(String user1ID, String user2ID, String user1EncSharedSecret, String user2EncSharedSecret) {
        // conversationID = lastConversationID++;
        // this.hidden = false;
        // this.user1ID = user1ID;
        // this.user2ID = user2ID;
        // this.user1EncSharedSecret = user1EncSharedSecret;
        // this.user2EncSharedSecret = user2EncSharedSecret;
        // this.createdTimestamp = Instant.now();
        conversationID(lastConversationID++);
        this.hidden(false);
        this.user1ID(user1ID);
        this.user2ID(user2ID);
        this.user1EncSharedSecret(user1EncSharedSecret);
        this.user2EncSharedSecret(user2EncSharedSecret);
        this.createdTimestamp(Instant.now());
    }

    // Getters & Setters
    public int conversationID() { return this.conversationID; }
    public void conversationID(int conversationID) { this.conversationID = conversationID; }

    public boolean hidden() { return this.hidden; }
    public void hidden(boolean hidden) { this.hidden = hidden; }

    public String user1ID() { return this.user1ID; }
    public void user1ID(String user1ID) { this.user1ID = user1ID; }

    public String user2ID() { return this.user2ID; }
    public void user2ID(String user2ID) { this.user2ID = user2ID; }

    public String user1EncSharedSecret() { return this.user1EncSharedSecret; }
    public void user1EncSharedSecret(String user1EncSharedSecret) { this.user1EncSharedSecret = user1EncSharedSecret; }

    public String user2EncSharedSecret() { return this.user2EncSharedSecret; }
    public void user2EncSharedSecret(String user2EncSharedSecret) { this.user2EncSharedSecret = user2EncSharedSecret; }

    public Instant createdTimestamp() { return this.createdTimestamp; }
    public void createdTimestamp(Instant createdTimestamp) { this.createdTimestamp = createdTimestamp; }
}
