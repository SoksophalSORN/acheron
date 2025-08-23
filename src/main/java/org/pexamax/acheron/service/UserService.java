package org.pexamax.acheron.service;

import org.pexamax.acheron.Util;
import org.pexamax.acheron.dao.UserDao;
import org.pexamax.acheron.model.User;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    // Dependency injection of UserDao -- by Springboot
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getByUsername(String username) {
        User user = userDao.getByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        else
            return user;
    }

    public User getByID(long userID) {
        User user = userDao.getByID(userID);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        else
            return user;
    }

    public boolean changeUsername(String username) {
        // if (username exists in the database) { return false; }
        // update username in the database
        // this.username = username;
        return true;
        // }
        // return false;
    }

    // public boolean isEmailVerified() {
    //     return emailVerified;
    // }

    public boolean changeEmail(String email) {
        // if (email exists in the database) { return false; }
        // update email in the database
        // this.email = email;
        return true;
        // }
        // return false;
    }

    public User login(String email, String password) {
        User currentUser = userDao.login(email, password);
        if (currentUser == null)
            throw new IllegalArgumentException("Invalid email or password");
        else
            return currentUser;
    }

    // Method for registering a new user
    public User register(String username, String email, String password) {
        if (userDao.isExists(username, email)) {
            throw new IllegalArgumentException("Username or email already exists.");
        }
        User newUser = userDao.register(username, email, password);
        if (newUser == null)
            throw new IllegalArgumentException("Registration failed. User already exists or invalid data.");
        else
            return newUser;
    }

    public boolean deleteUser(long userID, String password) {
        if (userDao.deleteUser(userID, password)) {
            return true;
        } else throw new IllegalArgumentException("User deletion failed. You may not be the user.");
    }

    public byte[] getUserEdPublicKey(long userID) {
        // Retrieve public key from database by userID
        byte[] publicKey = Util.utf8ToBytes("publicKey"); // placeholder
        return publicKey;
    }

}
