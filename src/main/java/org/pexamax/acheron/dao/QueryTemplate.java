package org.pexamax.acheron.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class QueryTemplate {
    private static JdbcTemplate template;

    @Autowired
    public static void set(JdbcTemplate jdbcTemplate) {
        template = jdbcTemplate;
    }

    public static JdbcTemplate get() {
        return template;
    }
}
