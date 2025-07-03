package org.pexamax.acheron;

import java.time.Instant;

public class Conversation {


	private long conversationID; 
	private int lastConversationID = 0;  // Replace with value from DB

	private boolean hidden;
	private long user1ID;
	private long user2ID;
	private String user1EncSharedSecret;
	private String user2EncSharedSecret;
	private Instant createdTimestamp;
	private Instant lastMessageSentTimestamp;
	private Long blockerID;

	public Conversation(
		long user1ID,
		long user2ID,
		String user1EncSharedSecret,
		String user2EncSharedSecret
	) {
        this.conversationID = lastConversationID++;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.user1EncSharedSecret = user1EncSharedSecret;
        this.user2EncSharedSecret = user2EncSharedSecret;
        this.createdTimestamp = Instant.now();
        this.lastMessageSentTimestamp = Instant.now();
        this.hidden = false; // Default to not hidden
	}
}
