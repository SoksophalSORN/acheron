// Package and Imports
package org.pexamax.acheron;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ConversationTest {

    @Autowired
    private JdbcTemplate template;

    @Test
    void testSelectQuery() {
        Conversation conversation1 = new Conversation(1, 2);
        conversation1.setJdbcTemplate(template);
        conversation1.retrieveMessages(201, 20);
        conversation1.displayMessages();
    }
}

