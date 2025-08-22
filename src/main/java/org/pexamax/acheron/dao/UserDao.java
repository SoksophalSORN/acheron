package org.pexamax.acheron.dao;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.Util;


import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserDao {

    private final JdbcTemplate template;

    // JDBC Dependency Injection -- Done automatically by Spring Boot
    @Autowired
    public UserDao(JdbcTemplate template) {
        this.template = template;
    }

    public User getByUsername(String username) {
        String sql = "SELECT user_id, username, email, email_verified, public_key, enc_private_key FROM user WHERE username = ?";
        List<User> fetchedUser = template.query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null)
                            ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null)
                            ? encEdPrivateKeyBlob.getBytes(1, (int) encEdPrivateKeyBlob.length())
                            : null;

                    return new User(
                            rs.getLong("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            null, // Password is not retrieved here
                            rs.getBoolean("email_verified"),
                            EdPublicKeyBytes,
                            encEdPrivateKeyBytes);
                },
                username);

        if (fetchedUser.isEmpty() || fetchedUser.getFirst() == null) {
            return null;
        } else {
            return fetchedUser.getFirst();
        }
    }

    public User getByID(long userID) {
        String sql = "SELECT user_id, username, email, email_verified, public_key, enc_private_key FROM user WHERE user_id = ?";
        List<User> fetchedUser = template.query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null)
                            ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null)
                            ? encEdPrivateKeyBlob.getBytes(1, (int) encEdPrivateKeyBlob.length())
                            : null;

                    return new User(
                            rs.getLong("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            null, // Password is not retrieved here
                            rs.getBoolean("email_verified"),
                            EdPublicKeyBytes,
                            encEdPrivateKeyBytes);
                },
                userID);

        if (fetchedUser.isEmpty() || fetchedUser.getFirst() == null) {
            return null;
        } else {
            return fetchedUser.getFirst();
        }
    }

    public User login(String email, String password) {
        String sql = "SELECT user_id, username, email, email_verified, password_hash, public_key, enc_private_key, message_destruct_timer FROM user WHERE email = ?";
        String[] passwordHash = new String[1];
        List<User> fetchedUser = template.query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null)
                            ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null)
                            ? encEdPrivateKeyBlob.getBytes(1, (int) encEdPrivateKeyBlob.length())
                            : null;
                    passwordHash[0] = rs.getString("password_hash");

                    return new User(
                            rs.getLong("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            password,
                            rs.getBoolean("email_verified"),
                            EdPublicKeyBytes,
                            encEdPrivateKeyBytes);
                },
                email);

        if (fetchedUser.isEmpty() || fetchedUser.getFirst() == null) {
            return null;
        } else {
            return (Util.verifyPassword(password, passwordHash[0])) ? fetchedUser.getFirst() : null;
        }
    }

    // Method to check if username or email exists
    public boolean isExists(String username, String email) {
        String sql = "SELECT COUNT(1) FROM user WHERE username = ? OR email = ?";
        long count = template.queryForObject(sql, Integer.class, username, email);
        return (count > 0) ? true : false;
    }

    // Method for registering a new user
    public User register(String username, String email, String password) {
        User newUser = new User(username, email, password);
        String sql = "INSERT INTO user (username, email, email_verified, password_hash, public_key, enc_private_key, message_destruct_timer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = template.update(sql, newUser.getUsername(), newUser.getEmail(), false,
                Util.hashPassword(password), newUser.getEdPublicKey(),
                Util.encryptPrivateKey(password, newUser.getEdPrivateKey()), 0);
        if (rowsAffected > 0)
            return newUser;
        else
            return null;
    }
}
