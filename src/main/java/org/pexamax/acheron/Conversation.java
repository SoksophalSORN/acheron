package org.pexamax.acheron;

import org.pexamax.acheron.Message;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.List;

import java.sql.Blob;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Conversation extends Connection implements Persistable {
    private boolean hidden;
    private String sharedSecret;
    private String user1EncSharedSecret;
    private String user2EncSharedSecret;
    private Instant lastMessageSentTimestamp;
    private long blockerID;
    private int destructTimer;

    private JdbcTemplate template;

    private LinkedList<Message> messages = new LinkedList<Message>(); // fetch 20 messages at a time

    // To keep track of all the conversations user has
    // Ordering by the latest lastMessageSentTimestamp while having conversationID
    // as a tiebreaker to avoid merging
    private static Comparator<Conversation> reverseComparator = Comparator
            .comparing((Conversation convo) -> convo.lastMessageSentTimestamp, Comparator.reverseOrder())
            .thenComparing(convo -> convo.getID());
    private static TreeSet<Conversation> conversations = new TreeSet<Conversation>(reverseComparator);
    private static TreeSet<Conversation> hiddenConversations = new TreeSet<Conversation>(reverseComparator);

    // For creating a new conversation
    public Conversation(long user1ID, long user2ID) {
        super(user1ID, user2ID);
        setHidden(false);
        setLastMessageSentTimestamp(Instant.now());
        setSharedSecret("sharedSecret"); // This should be generated securely
        setBlockerID(0); // No blocker initially
        setDestructTimer(0); // No destruct timer initially
        // String user1EncSharedSecret = asymEncrypt(sharedSecret,
        // User.getUserPublicKey(user1ID));
        // String user2EncSharedSecret = asymEncrypt(sharedSecret,
        // User.getUserPublicKey(user2ID));
    }

    // For retrieving data from the database
    public Conversation(
            long conversationID,
            boolean hidden,
            long user1ID,
            long user2ID,
            String user1EncSharedSecret,
            String user2EncSharedSecret,
            Instant createdTimestamp,
            Instant lastMessageSentTimestamp,
            long blockerID,
            int destructTimer,
            // All of the above params are retrieved from the database
            String currentUserID, // retrieve from client
            String privateKey // retrieve from client
    ) {
        super(conversationID, user1ID, user2ID, createdTimestamp);
        this.setHidden(hidden);
        this.setUser1EncSharedSecret();
        this.setUser2EncSharedSecret();
        this.setLastMessageSentTimestamp(lastMessageSentTimestamp);
        this.setBlockerID(blockerID);
        this.setDestructTimer(destructTimer);
        // this.sharedSecret = asymDecrypt(currentUserID.equals(user1ID) ?
        // user1EncSharedSecret : user2EncSharedSecret, privateKey);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    // Fetch messages for a conversation
    public void retrieveMessages(long conversation_id, int limit) {
        if (this.messages.isEmpty()) {
            String sql = "SELECT * FROM message WHERE conversation_id = ? ORDER BY sent_time DESC LIMIT ?";
            List<Message> fetchedMessages = template.query(
                    sql,
                    (rs, rowNum) -> {
                        Blob contentBlob = rs.getBlob("enc_content");
                        byte[] encContent = (contentBlob != null) ? contentBlob.getBytes(1, (int) contentBlob.length()) : null;

                        Timestamp readTimeTs = rs.getTimestamp("read_time");
                        Instant readTime = (readTimeTs != null) ? readTimeTs.toInstant() : null;

                        return new Message(
                            rs.getLong("message_id"),
                            rs.getLong("conversation_id"),
                            rs.getInt("destruct_timer"),
                            rs.getTimestamp("sent_time").toInstant(),
                            readTime,
                            rs.getLong("sender_id"),
                            rs.getString("content_type"),
                            encContent,
                            rs.getString("digital_signature"),
                            this.sharedSecret
                        );
                    },
                    conversation_id, limit
                );

            if (fetchedMessages != null && !fetchedMessages.isEmpty()) {
                this.messages.addAll(fetchedMessages);
                System.out.println("Fetched " + fetchedMessages.size() + " message objects from the database");
            } else throw new IllegalStateException("No messages found for conversation ID: " + conversation_id);

        } else {
            String sql = "SELECT * FROM message WHERE sent_time < ? AND conversation_id = ? ORDER BY sent_time DESC LIMIT ?";
            List<Message> fetchedMessages = template.query(
                    sql,
                    (rs, rowNum) -> new Message(
                        rs.getLong("message_id"),
                        rs.getLong("conversation_id"),
                        rs.getInt("destruct_timer"),
                        rs.getDate("sent_time").toInstant(),
                        rs.getDate("read_time").toInstant(),
                        rs.getLong("sender_id"),
                        rs.getString("content_type"),
                        rs.getBlob("content").getBytes(0, (int) rs.getBlob("content").length()),
                        rs.getString("digital_signature"),
                        this.sharedSecret
                    ),
                    messages.getLast().getSentTime(), limit 
                );
            if (fetchedMessages != null && !fetchedMessages.isEmpty()) {
                this.messages.addAll(fetchedMessages);
                System.out.println("Fetched " + fetchedMessages.size() + " message objects from the database");
            } else throw new IllegalStateException("No messages found for conversation ID: " + conversation_id);
        }
    }

    public void displayMessages() {
        for (Message message : messages) {
            System.out.println(message.toString());
        }
    }

    private void setHidden(boolean hidden) {
        if (this.hidden == hidden) return; 
        this.hidden = hidden;
    }

    private void setSharedSecret(String sharedSecret) {
        if (sharedSecret != null && !sharedSecret.isEmpty()) this.sharedSecret = sharedSecret;
        else throw new IllegalArgumentException("Shared secret cannot be null or empty");
    }

    private void setUser1EncSharedSecret() {
        if (user1EncSharedSecret != null && !user1EncSharedSecret.isEmpty()) {
            // Use User1's public key to encrypt the shared secret
            this.user1EncSharedSecret = "encryptedSharedSecretUsingUser1PublicKey";
        } else throw new IllegalArgumentException("User 1 encrypted shared secret cannot be null or empty");
    }

    private void setUser2EncSharedSecret() {
        if (user1EncSharedSecret != null && !user1EncSharedSecret.isEmpty()) {
            // Use User2's public key to encrypt the shared secret
            this.user1EncSharedSecret = "encryptedSharedSecretUsingUser2PublicKey";
        } else throw new IllegalArgumentException("User 2 encrypted shared secret cannot be null or empty");
    }

    private void setLastMessageSentTimestamp(Instant lastMessageSentTimestamp) {
        if (lastMessageSentTimestamp != null) this.lastMessageSentTimestamp = lastMessageSentTimestamp;
        else throw new IllegalArgumentException("Current timestamp cannot be null");
    }

    public void setBlockerID(long blockerID) {
        if (blockerID >= 0 && blockerID != super.getInitiatorID() && blockerID != super.getReceiverID()) this.blockerID = blockerID;
        else throw new IllegalArgumentException("Blocker ID cannot be null or empty");
    }

    public void setDestructTimer(int timer) {
        int oneWeekInSeconds = 604800;
        if (timer < 0) this.destructTimer = 0; // No destruct timer
        else if (timer > oneWeekInSeconds) this.destructTimer = oneWeekInSeconds; // Max 7 days 
        // update the database with the new destruct timer
    }

    public int getDestructTimer() {
        return this.destructTimer;
    }

    public static void retrieveConversations() {
        // Retrieve 10 conversations at a time based on the userID
        // Then add them to their respective collection whether they are hidden or not
    }

    public static void listConversations(boolean hidden) {
        // List all visible/hidden conversations for the user
    }

    public static void selectConversation(int index, boolean hidden) {
        // Select the conversation at the specified index
        // Display the messages in the conversation
    }

    public static boolean areUsersConnected(long user1ID, long user2ID) {
        // Query for the connections between user1 and user2 from conversation table
        // If a connection exists the table, return true
        // else:
        return false;
    }

    protected boolean deleteConnection() {
        // Used to delete a conversation
        // Delete the conversation from the database first
        // Then empty the messages collection 
        // And finally delete the conversation object from the conversations collection
        // return true when succeed, false when failed
        return false;
    }

    @Override
    public void load() {
        // load message from the database
        // This will be called when the user selects a conversation
    }

    @Override
    public void save() {
        // Save the conversation's metadata to the database
        // This will be called when a field of a conversation object is being changed
    }

    @Override
    public String toString() {
        return super.toString() + "isHidden: " + this.hidden + "\n lastMessageSentTimestamp: " + this.lastMessageSentTimestamp + "\n blockerID: " + this.blockerID + "\n destructTimer: " + this.destructTimer + "\n"; }

    @Override
    public boolean equals(Object convo) {
        if (convo == null || !(convo instanceof Conversation)) return false;
        return (convo instanceof Conversation) && 
            this.hidden == ((Conversation)convo).hidden && 
            this.lastMessageSentTimestamp.equals(((Conversation)convo).lastMessageSentTimestamp) &&
            this.blockerID == ((Conversation)convo).blockerID &&
            this.sharedSecret.equals(((Conversation)convo).sharedSecret) &&
            this.destructTimer == ((Conversation)convo).destructTimer;
    }

    public void delete() {
        super.deleteConnection("Conversation");
        // Delete the conversation from the database
        // This will be called when the user deletes a conversation 
    }

    public boolean equals(Connection conn) {
        return super.equals(conn);
    }
}
