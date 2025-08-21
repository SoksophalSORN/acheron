package org.pexamax.acheron.dao;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.Util;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;;

public class UserDao {

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

    public static User register(String username, String email, String password) {
        return null; // Registration logic to be implemented
    }
}
