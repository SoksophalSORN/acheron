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
        // messageID = lastMessageID++;
        // this.conversationID = conversationID;
        // this.destructTime = destructTime;
        // this.sentTime = Instant.now();
        // this.senderID = senderID;
        // this.contentType = contentType;
        // this.encContent = encContent;
        // this.digitalSignature = digitalSignature;
        this.messageID(lastMessageID++);
        this.conversationID(conversationID);
        this.destructTime(destructTime);
        this.sentTime(Instant.now());
        this.contentType(contentType);
        this.encContent(encContent);
        this.digitalSignature(digitalSignature);
    }

    public long messageID() { return this.messageID; }
    public void messageID(long messageID) { this.messageID = messageID; }

    public int conversationID() { return this.conversationID; }
    public void conversationID(int conversationID) { this.conversationID = conversationID; }

    public int destructTime() { return this.destructTime; }
    public void destructTime(int destructTime) { this.destructTime = destructTime; }

    public Instant sentTime() { return this.sentTime; } 
    public void sentTime(Instant sentTime) { this.sentTime = sentTime; } 

    public int senderID() { return this.senderID; }
    public void senderID(int senderID) { this.senderID = senderID; }

    public String contentType() { return this.contentType; }
    public void contentType(String contentType) { this.contentType = contentType; }
    
    public byte[] encContent() { return this.encContent; }
    public void encContent(byte[] encContent) { this.encContent = encContent; }
    
    public String digitalSignature() { return this.digitalSignature; }
    public void digitalSignature(String digitalSignature) { this.digitalSignature = digitalSignature; }

    public Instant receivedTime() { return this.receivedTime; };
    public void receivedTime(Instant receivedTime) { this.receivedTime = receivedTime; };
}
