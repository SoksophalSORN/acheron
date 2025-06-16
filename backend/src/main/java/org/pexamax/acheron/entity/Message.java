package org.pexamax.acheron.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
class Message {
    // private static int lastMessageID = 0;  // Replace with value from DB

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID; // Auto-generated and auto-incremented by JPA

    private Long conversationID;
    private int destructTime;
    private Instant sentTime;
    private Instant readTime;
    private UUID senderID;
    private String contentType;
    private byte[] encContent;
    private String digitalSignature;

    public Message(Long conversationID, Instant senderID, String contentType, byte[] encContent, String digitalSignature, int destructTime) {
        // messageID = lastMessageID++;
        // this.conversationID = conversationID;
        // this.destructTime = destructTime;
        // this.sentTime = Instant.now();
        // this.senderID = senderID;
        // this.contentType = contentType;
        // this.encContent = encContent;
        // this.digitalSignature = digitalSignature;
        // this.messageID(lastMessageID++);
        this.conversationID(conversationID);
        this.destructTime(destructTime);
        this.sentTime(Instant.now());
        this.contentType(contentType);
        this.encContent(encContent);
        this.digitalSignature(digitalSignature);
    }

    public long messageID() { return this.messageID; }
    public void messageID(long messageID) { this.messageID = messageID; }

    public Long conversationID() { return this.conversationID; }
    public void conversationID(Long conversationID) { this.conversationID = conversationID; }

    public int destructTime() { return this.destructTime; }
    public void destructTime(int destructTime) { this.destructTime = destructTime; }

    public Instant sentTime() { return this.sentTime; } 
    public void sentTime(Instant sentTime) { this.sentTime = sentTime; } 

    public UUID senderID() { return this.senderID; }
    public void senderID(UUID senderID) { this.senderID = senderID; }

    public String contentType() { return this.contentType; }
    public void contentType(String contentType) { this.contentType = contentType; }
    
    public byte[] encContent() { return this.encContent; }
    public void encContent(byte[] encContent) { this.encContent = encContent; }
    
    public String digitalSignature() { return this.digitalSignature; }
    public void digitalSignature(String digitalSignature) { this.digitalSignature = digitalSignature; }

    public Instant readTime() { return this.readTime; };
    public void readTime(Instant readTime) { this.readTime = readTime; };
}
