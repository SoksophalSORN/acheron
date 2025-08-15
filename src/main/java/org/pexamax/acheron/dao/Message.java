package org.pexamax.acheron.dao;

import org.pexamax.acheron.model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Message {
    public static void retrieveMessages(long conversationId, int limit, LinkedList<model.Message> messages,
            byte[] decryptionKey, JdbcTemplate template) {
        String sql;
        if (messages.isEmpty())
            sql = "SELECT * FROM message WHERE conversation_id = ? ORDER BY sent_time DESC LIMIT ?";
        else
            sql = "SELECT * FROM message WHERE sent_time < ? AND conversation_id = ? ORDER BY sent_time DESC LIMIT ?";

        List<Message> fetchedMessages = template.query(
                sql,
                (rs, rowNum) -> {
                    Blob contentBlob = rs.getBlob("enc_content");
                    byte[] encContent = (contentBlob != null) ? contentBlob.getBytes(1, (int) contentBlob.length())
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
                            rs.getString("digital_signature"),
                            decryptionKey);
                },
                conversationId, limit);
        if (fetchedMessages != null && !fetchedMessages.isEmpty()) {
            messages.addAll(fetchedMessages);
            System.out.println("Fetched " + fetchedMessages.size() + " message objects from the database");
        } else
            throw new IllegalStateException("No messages found for conversation ID: " + getID());
    }
}