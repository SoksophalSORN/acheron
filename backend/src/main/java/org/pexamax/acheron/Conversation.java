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
        conversationID = lastConversationID++;
        this.hidden = false;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.user1EncSharedSecret = user1EncSharedSecret;
        this.user2EncSharedSecret = user2EncSharedSecret;
        this.createdTimestamp = Instant.now();
    }
}