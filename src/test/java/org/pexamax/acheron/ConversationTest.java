// Package and Imports
package org.pexamax.acheron;

import org.junit.jupiter.api.Test;
import org.pexamax.acheron.model.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ConversationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void messageQueryTest() {
        Conversation conversation = new Conversation(101, 102);
        conversation.retrieveMessages(5);
    }
}
