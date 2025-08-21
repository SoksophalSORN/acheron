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
    private byte[] digitalSignature;

    // For creating a new message
    public Message(long conversationID, int destructTime, long senderID, String contentType, byte[] content, byte[] EdPrivateKey, byte[] encryptionKey) {
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.sentTime = Instant.now(); // Set sentTime to current time
        this.readTime = null; // Initially, readTime is null
        this.contentType = contentType;
        this.destructTime = destructTime; // From conversation settings
        this.content = Util.symEncrypt(encryptionKey, content); // encrypt the content
        this.digitalSignature = Util.signEncMessage(EdPrivateKey, this.content);// sign the encrypted content with the sender's private key
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
            byte[] digitalSignature,
            // All of the above params are fetched from the database
            byte[] decryptionKey, // fetch from conversation
            byte[] senderEdPublicKey // fetch from sender's profile
    ) {
        this.messageID = messageID;
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.contentType = contentType;
        this.sentTime = sentTime;
        this.readTime = readTime;
        this.destructTime = destructTime;
        this.digitalSignature = digitalSignature;     
        boolean isValid = Util.verifySignature(senderEdPublicKey, encContent, digitalSignature); // Verify the signature with the sender's public key
        if (!isValid) {
            this.content = "message corrupted".getBytes();
            throw new IllegalArgumentException("Invalid digital signature for message ID: " + messageID);
        } else this.content = Util.symDecrypt(decryptionKey, encContent);
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

    public byte[] getDigitalSignature() {
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
                + Util.bytesToBase64(getDigitalSignature());
    }
}
