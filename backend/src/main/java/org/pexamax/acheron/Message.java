package org.pexamax.acheron;

import java.time.Instant;

class Message {
    private static int lastMessageID = 0;  // Replace with value from DB

    private long messageID;
    private int conversationID;
    private int destructTime;
    private Instant sentTime;
    private Instant receivedTime;
    private int senderID;
    private String contentType;
    private byte[] encContent;
    private String digitalSignature;

    public Message(int conversationID, int senderID, String contentType, byte[] encContent, String digitalSignature, int destructTime) {
        messageID = lastMessageID++;
        this.conversationID = conversationID;
        this.destructTime = destructTime;
        this.sentTime = Instant.now();
        this.senderID = senderID;
        this.contentType = contentType;
        this.encContent = encContent;
        this.digitalSignature = digitalSignature;
    }
}