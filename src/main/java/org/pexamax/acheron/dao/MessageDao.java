package org.pexamax.acheron.dao;

import org.pexamax.acheron.model.Message;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class MessageDao {

        public static void retrieveMessages(long conversationId, int limit, LinkedList<Message> messages,
                        byte[] decryptionKey, byte[] senderEdPrivateKey) {
                String sql;
                if (messages.isEmpty()) {
                        sql = "SELECT * FROM message WHERE conversation_id = ? ORDER BY sent_time DESC LIMIT ?";
                        List<Message> fetchedMessages = QueryTemplate.get().query(
                                        sql,
                                        (rs, rowNum) -> {
                                                Blob contentBlob = rs.getBlob("enc_content");
                                                byte[] encContent = (contentBlob != null)
                                                                ? contentBlob.getBytes(1, (int) contentBlob.length())
                                                                : null;

                                                Blob signatureBlob = rs.getBlob("digital_signature");
                                                byte[] signature = (signatureBlob != null)
                                                                ? signatureBlob.getBytes(1,
                                                                                (int) signatureBlob.length())
                                                                : null;

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
                                                                signature,
                                                                decryptionKey,
                                                                senderEdPrivateKey);
                                        },
                                        conversationId, limit);

                        if (fetchedMessages != null && !fetchedMessages.isEmpty()) {
                                for (Message message : fetchedMessages) {
                                        messages.addFirst(message);
                                }
                                System.out.println("Fetched " + fetchedMessages.size()
                                                + " message objects from the database");
                        } else
                                throw new IllegalStateException(
                                                "No messages found for conversation ID: " + conversationId);

                } else {
                        sql = "SELECT * FROM message WHERE sent_time < ? AND conversation_id = ? ORDER BY sent_time DESC LIMIT ?";
                        List<Message> fetchedMessages = QueryTemplate.get().query(
                                        sql,
                                        (rs, rowNum) -> {
                                                Blob contentBlob = rs.getBlob("enc_content");
                                                byte[] encContent = (contentBlob != null)
                                                                ? contentBlob.getBytes(1, (int) contentBlob.length())
                                                                : null;

                                                Blob signatureBlob = rs.getBlob("digital_signature");
                                                byte[] signature = (signatureBlob != null)
                                                                ? signatureBlob.getBytes(1,
                                                                                (int) signatureBlob.length())
                                                                : null;

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
                                                                signature,
                                                                decryptionKey,
                                                                senderEdPrivateKey);
                                        },
                                        messages.getLast().getSentTime(), conversationId, limit);
                        if (fetchedMessages != null && !fetchedMessages.isEmpty()) {
                                messages.addAll(fetchedMessages);
                                System.out.println("Fetched " + fetchedMessages.size()
                                                + " message objects from the database");
                        } else
                                throw new IllegalStateException(
                                                "No messages found for conversation ID: " + conversationId);
                }
        }

        public static void saveMessage(Message message) {
                String sql = "INSERT INTO message (conversation_id, destruct_timer, sent_time, sender_id, content_type, enc_content, digital_signature) VALUES (?, ?, ?, ?, ?, ?, ?)";
                QueryTemplate.get().update(sql,
                                message.getConversationID(),
                                message.getDestructTime(),
                                Timestamp.from(message.getSentTime()),
                                message.getSenderID(),
                                message.getContentType(),
                                message.getContent(), // Encrypt first before sending
                                message.getDigitalSignature());
                System.out.println("Message saved to database: " + message);
        }
}
