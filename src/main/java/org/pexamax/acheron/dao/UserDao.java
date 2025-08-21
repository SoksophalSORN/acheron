package org.pexamax.acheron.dao;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.Util;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {

    public static User login(String email, String password, JdbcTemplate template) {
        String passwordHash = Util.hashPassword(password);
        String sql = "SELECT user_id, username, email, email_verified, password_hash, public_key, enc_private_key, message_destruct_timer FROM user WHERE email = ? AND password_hash = ?";
        List<User> fetechedUser = template.query(
                sql,
                (rs, rowNum)
            );
    }
}
