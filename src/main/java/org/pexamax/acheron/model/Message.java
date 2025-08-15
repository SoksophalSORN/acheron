package org.pexamax.acheron.model;

import java.time.Instant;

import org.pexamax.acheron.Util;

public class Message {

    private long messageID;

    private long conversationID;
    private int destructTime;
    private Instant sentTime;
    private Instant readTime;
    private long senderID;
    private String contentType; // Only text for now, can't display image on terminal lol
    private byte[] content;
    private String digitalSignature;

    // For creating a new message
    public Message(long conversationID, int destructTime, long senderID, String contentType, byte[] content) {
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.sentTime = Instant.now(); // Set sentTime to current time
        this.readTime = null; // Initially, readTime is null
        this.contentType = contentType;
        this.destructTime = destructTime; // From conversation settings
        // encContent = symEncrypt(content, sharedSecret) encrypt the content
        this.digitalSignature = ""; // sign the encrypted content with the sender's private key
        // send to the database
    }

    // For receiving a message
    public Message(
            long messageID,
            long conversationID,
            int destructTime,
            Instant sentTime,
            Instant readTime,
            long senderID,
            String contentType,
            byte[] encContent,
            String digitalSignature,
            // All of the above params are fetched from the database
            byte[] decryptionKey // fetch from conversation
    ) {
        this.messageID = messageID;
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.contentType = contentType;
        this.content = symDecrypt(decryptionKey, encContent);
        this.sentTime = sentTime;
        this.readTime = readTime;
        this.destructTime = destructTime;
        this.digitalSignature = digitalSignature; // Verify the signature with the sender's public key
    }

    public long getMessageID() {
        return messageID;
    }

    public long getConversationID() {
        return conversationID;
    }

    public int getDestructTime() {
        return destructTime;
    }

    public Instant getSentTime() {
        return sentTime;
    }

    public Instant getReadTime() {
        return readTime;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public long getSenderID() {
        return senderID;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public String toString() {
        return getMessageID() + "\t" + getConversationID() + "\t" + getDestructTime() + "\t" + getSenderID() + "\t"
                + getSentTime() + "\t" + getContentType() + "\t" + Util.bytesToUTF8(getContent()) + "\t"
                + getDigitalSignature();
    }
}
