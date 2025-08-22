// Package and Imports
// Test command: ./mvnw test -Dtest=UserTest
package org.pexamax.acheron;

import org.pexamax.acheron.dao.*;

import org.junit.jupiter.api.Test;
import org.pexamax.acheron.model.User;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserDao UserDao;

    // @Test
    // public void userRegistrationErrorTest() {
    //     // Invalid Case
    //     IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
    //         User Alice = UserDao.register("alice", "alice@example.com", "securepassword");
    //     });
    //
    //     IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
    //         User Alice1 = UserDao.register("alice1", "alice@example.com", "securepassword");
    //     });
    //
    //     IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> {
    //         User Alice2 = UserDao.register("alice", "alice1@example.com", "securepassword");
    //     });
    //
    //     assertEquals("Username or email already exists.", ex1.getMessage());
    //     assertEquals("Username or email already exists.", ex2.getMessage());
    //     assertEquals("Username or email already exists.", ex3.getMessage());
    // }

    // @Test
    // public void userReqistrationSuccessTest() {
    //     // Valid Case
    //     // User Sophal = User.register("sophal", "sophal@example.com", "securepassword",
    //     // QueryTemplate.get()); done already before
    //
    //     IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
    //         User Sophal2 = UserDao.register("sophal", "sophal@example.com", "securepassword");
    //     });
    //     assertEquals("Username or email already exists.", ex.getMessage());
    // }

    @Test
    public void userLoginTestWithDao() {
        // Valid Case
        User Sophal = UserDao.login("sophal@example.com", "securepassword");
        System.out.println(Sophal);
    }
}
