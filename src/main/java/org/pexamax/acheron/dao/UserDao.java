package org.pexamax.acheron.dao;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.Util;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class UserDao {

    public static User getByUsername(String username) {
        String sql = "SELECT user_id, username, email, email_verified, public_key, enc_private_key FROM user WHERE username = ?";
        List<User> fetchedUser = QueryTemplate.get().query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null)
                            ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null)
                            ? encEdPrivateKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
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

    public static User getByID(long userID) {
        String sql = "SELECT user_id, username, email, email_verified, public_key, enc_private_key FROM user WHERE user_id = ?";
        List<User> fetchedUser = QueryTemplate.get().query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null)
                            ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null)
                            ? encEdPrivateKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
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

    public static User login(String email, String password) {
        String sql = "SELECT user_id, username, email, email_verified, password_hash, public_key, enc_private_key, message_destruct_timer FROM user WHERE email = ? AND password_hash = ?";
        List<User> fetchedUser = QueryTemplate.get().query(
                sql,
                (rs, rowNum) -> {
                    Blob EdPublicKeyBlob = rs.getBlob("public_key");
                    byte[] EdPublicKeyBytes = (EdPublicKeyBlob != null)
                            ? EdPublicKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;
                    Blob encEdPrivateKeyBlob = rs.getBlob("enc_private_key");
                    byte[] encEdPrivateKeyBytes = (encEdPrivateKeyBlob != null)
                            ? encEdPrivateKeyBlob.getBytes(1, (int) EdPublicKeyBlob.length())
                            : null;

                    return new User(
                            rs.getLong("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            password,
                            rs.getBoolean("email_verified"),
                            EdPublicKeyBytes,
                            encEdPrivateKeyBytes);
                },
                email, Util.hashPassword(password));

        if (fetchedUser.isEmpty() || fetchedUser.getFirst() == null) {
            return null;
        } else {
            return fetchedUser.getFirst();
        }
    }

    // Method to check if username or email exists
    public static boolean isExists(String username, String email) {
        String sql = "SELECT COUNT(1) FROM user WHERE username = ? OR email = ?";
        long count = QueryTemplate.get().queryForObject(sql, Integer.class, username, email);
        return (count > 0) ? true : false;
    }

    // Method for registering a new user
    public static User register(String username, String email, String password) {
        User newUser = new User(username, email, password);
        String sql = "INSERT INTO user (username, email, email_verified, password_hash, public_key, enc_private_key, message_destruct_timer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = QueryTemplate.get().update(sql, newUser.getUsername(), newUser.getEmail(), false,
                Util.hashPassword(password), newUser.getEdPublicKey(),
                Util.encryptPrivateKey(password, newUser.getEdPrivateKey()), 0);
        if (rowsAffected > 0)
            return newUser;
        else
            return null;
    }
}
