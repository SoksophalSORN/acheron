package org.pexamax.acheron.dao;

import org.pexamax.acheron.model.Conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ConversationDao {

    public static void Hello() { System.out.println("Hello");}

    public static void simpleQuery() {
        System.out.println("Hello");
        String sql = "SELECT * FROM conversation WHERE user1_id = ?;";
        List<Conversation> fetchedConvo = QueryTemplate.get().query(
                sql,
                (rs, rowNum) -> new Conversation(
                    rs.getLong("conversation_id"),
                    rs.getBoolean("hidden"),
                    rs.getLong("user1_id"),
                    rs.getLong("user2_id"),
                    rs.getTimestamp("created_timestamp").toInstant(),
                    rs.getTimestamp("last_sent_timestamp").toInstant(),
                    rs.getLong("blocker"),
                    rs.getInt("message_destruct_timer"),
                    201, "secure_privateKey".getBytes()
                    ),
                102
            );
        System.out.println("Fetched " + fetchedConvo.size());
        for (Conversation convo : fetchedConvo) {
            System.out.println(convo.toString());
        }
    }
}
