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
        String sql = "SELECT user_id, username, email, email_verified, password_hash, public_key, enc_private_key, message_destruct_timer FROM user WHERE email = ? AND password_hash = ?";
        List<User> fetechedUser = template.query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null) ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length()) : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null) ? encEdPrivateKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length()) : null;

                    return new User(
                            rs.getLong("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            password,
                            rs.getBoolean("email_verified"),
                            EdPublicKeyBytes,
                            encEdPrivateKeyBytes
                        );
                },
                email, Util.hashPassword(password)
            );

        if (fetechedUser.isEmpty() || fetechedUser.getFirst() == null) {
            return null;
        } else {
            return fetechedUser.getFirst();
        }
    }
}
