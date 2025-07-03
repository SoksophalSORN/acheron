package org.pexamax.acheron;

import java.time.Instant;

public class Message {

	private long messageID; 

	private Long conversationID;
	private int destructTime;
	private Instant sentTime;
	private Instant readTime; private Long senderID;
	private String contentType;
	private byte[] encContent;
	private String digitalSignature;

	public Message(
		Long conversationID,
		Long senderID,
		String contentType,
		byte[] encContent,
		String digitalSignature,
		int destructTime
	) {
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.destructTime = destructTime;
        this.sentTime = Instant.now(); // Set sentTime to current time
        this.readTime = null; // Initially, readTime is null
        this.contentType = contentType;
        this.encContent = encContent;
        this.digitalSignature = digitalSignature;
	}

}
