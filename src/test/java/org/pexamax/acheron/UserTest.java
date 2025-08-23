// Package and Imports
// Test command: ./mvnw test -Dtest=UserTest
package org.pexamax.acheron;

import org.pexamax.acheron.service.UserService;
import org.pexamax.acheron.dao.UserDao;

import org.junit.jupiter.api.Test;
import org.pexamax.acheron.model.User;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Test // passed
    public void userRegistrationErrorTest() {
        // Invalid Case
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            User Alice = userService.register("alice", "alice@example.com", "securepassword");
        });

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            User Alice1 = userService.register("alice1", "alice@example.com", "securepassword");
        });

        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> {
            User Alice2 = userService.register("alice", "alice1@example.com", "securepassword");
        });

        assertEquals("Username or email already exists.", ex1.getMessage());
        assertEquals("Username or email already exists.", ex2.getMessage());
        assertEquals("Username or email already exists.", ex3.getMessage());
    }

    @Test // passed
    public void userLoginTestWithDao() {
        // Valid Case
        User Sophal = userService.register("sophal", "sophal@example.com", "securepassword");
        Sophal = userService.login("sophal@example.com", "securepassword");
        System.out.println(Sophal.toString());

    }

    @Test
    public void deleteUserTest() {
        User user = userService.getByUsername("sophal");
        userService.deleteUser(user.getUserID(), "securepassword");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            User deletedUser = userService.getByUsername("sophal");
        });
        assertEquals("User not found", ex.getMessage());
    }
}
