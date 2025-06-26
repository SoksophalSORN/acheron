package org.pexamax.acheron.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Conversation {

	// private static int lastConversationID = 0;  // Replace with value from DB

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long conversationID; // Auto-generated and auto-incremented by JPA

	private boolean hidden;
	private Long user1ID;
	private Long user2ID;
	private String user1EncSharedSecret;
	private String user2EncSharedSecret;
	private Instant createdTimestamp;
	private Instant lastMessageSentTimestamp;
	private Long blockerID;

	// Default constructor - required by JPA
	public Conversation() {}

	public Conversation(
		Long user1ID,
		Long user2ID,
		String user1EncSharedSecret,
		String user2EncSharedSecret
	) {
		this.setHidden(false);
		this.setUser1ID(user1ID);
		this.setUser2ID(user2ID);
		this.setUser1EncSharedSecret(user1EncSharedSecret);
		this.setUser2EncSharedSecret(user2EncSharedSecret);
		this.setCreatedTimestamp(Instant.now());
	}

	// Getters & Setters
	public Long getConversationID() {
		return this.conversationID;
	}

	public void setConversationID(Long conversationID) {
		this.conversationID = conversationID;
	}

	public boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public Long getUser1ID() {
		return this.user1ID;
	}

	public void setUser1ID(Long id) {
		user1ID = id;
	}

	public Long getUser2ID() {
		return this.user2ID;
	}

	public void setUser2ID(Long id) {
		user2ID = id;
	}

	public String getUser1EncSharedSecret() {
		return this.user1EncSharedSecret;
	}

	public void setUser1EncSharedSecret(String user1EncSharedSecret) {
		this.user1EncSharedSecret = user1EncSharedSecret;
	}

	public String getUser2EncSharedSecret() {
		return this.user2EncSharedSecret;
	}

	public void setUser2EncSharedSecret(String user2EncSharedSecret) {
		this.user2EncSharedSecret = user2EncSharedSecret;
	}

	public Instant getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Instant createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Instant getLastMessageSentTimestamp() {
		return this.lastMessageSentTimestamp;
	}

	public void setLastMessageSentTimestamp(Instant lastMessageSentTimestamp) {
		this.lastMessageSentTimestamp = lastMessageSentTimestamp;
	}

	public Long getBlockerID() {
		return this.blockerID;
	}

	public void setBlockerID(Long id) {
		blockerID = id;
	}
}
