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

    // @Test
    // void simpleQueryTest() {
    // String sql = "SELECT * FROM message;";
    // Conversation conversation2 = new Conversation(1, 2);
    // conversation2.setJdbcTemplate(template);
    // conversation2.simpleMessageQuery();
    // }
}
