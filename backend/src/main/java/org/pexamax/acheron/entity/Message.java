package org.pexamax.acheron.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Message {
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

    // Default constructor - required by JPA
    public Message() {}

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
        this.setConversationID(conversationID);
        this.setDestructTime(destructTime);
        this.setSentTime(Instant.now());
        this.setContentType(contentType);
        this.setEncContent(encContent);
        this.setDigitalSignature(digitalSignature);
    }

    public Long getMessageID() { return this.messageID; }
    public void setMessageID(Long messageID) { this.messageID = messageID; }

    public Long getConversationID() { return this.conversationID; }
    public void setConversationID(Long conversationID) { this.conversationID = conversationID; }

    public int getDestructTime() { return this.destructTime; }
    public void setDestructTime(int destructTime) { this.destructTime = destructTime; }

    public Instant getSentTime() { return this.sentTime; } 
    public void setSentTime(Instant sentTime) { this.sentTime = sentTime; } 

    public UUID getSenderID() { return this.senderID; }
    public void setSenderID(UUID senderID) { this.senderID = senderID; }

    public String getContentType() { return this.contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    
    public byte[] getEncContent() { return this.encContent; }
    public void setEncContent(byte[] encContent) { this.encContent = encContent; }
    
    public String getDigitalSignature() { return this.digitalSignature; }
    public void setDigitalSignature(String digitalSignature) { this.digitalSignature = digitalSignature; }

    public Instant getReadTime() { return this.readTime; };
    public void setReadTime(Instant readTime) { this.readTime = readTime; };

}
