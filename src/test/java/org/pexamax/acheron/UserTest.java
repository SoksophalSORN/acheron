// Package and Imports
// Test command: ./mvnw test -Dtest=UserTest
package org.pexamax.acheron;

import org.pexamax.acheron.dao.*;

import org.junit.jupiter.api.Test;
import org.pexamax.acheron.model.User;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class UserTest {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void userRegistrationErrorTest() {
        // Invalid Case
        QueryTemplate.set(template);
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            User Alice = User.register("alice", "alice@example.com", "securepassword");
        });

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            User Alice1 = User.register("alice1", "alice@example.com", "securepassword");
        });

        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> {
            User Alice2 = User.register("alice", "alice1@example.com", "securepassword");
        });

        assertEquals("Username or email already exists.", ex1.getMessage());
        assertEquals("Username or email already exists.", ex2.getMessage());
        assertEquals("Username or email already exists.", ex3.getMessage());
    }

    @Test
    public void userReqistrationSuccessTest() {
        // Valid Case
        QueryTemplate.set(template);
        // User Sophal = User.register("sophal", "sophal@example.com", "securepassword",
        // QueryTemplate.get()); done already before

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            User Sophal2 = User.register("sophal", "sophal@example.com", "securepassword");
        });
        assertEquals("Username or email already exists.", ex.getMessage());
    }

    @Test
    public void userLoginTestWithDao() {
        // Valid Case
        QueryTemplate.set(template);
        User Sophal = UserDao.login("sophal@example.com", "securepassword");
        System.out.println(Sophal);
    }

    // @Test
    // public void userLoginTest() {
    // // Valid Case
    // QueryTemplate.set(template);
    // User Sophal = User.login("sophal", "securepassword", QueryTemplate.get());
    // assertNotNull(Sophal);
    // // // Invalid Case
    // // IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
    // () -> {
    // // User Sophal2 = User.login("sophal", "wrongpassword", QueryTemplate.get());
    // // });
    // // assertEquals("Invalid username or password.", ex.getMessage());
    // }
}
