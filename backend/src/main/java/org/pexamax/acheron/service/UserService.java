// Package and Imports
package org.pexamax.acheron.service;

import org.pexamax.acheron.entity.User;
import org.pexamax.acheron.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database) 
               // or rolled back (reverted) if an error occurs
public class UserService {
    private final UserRepository usrRepo;
    // Business logic for user entity

    public UserService(UserRepository usrRepo) {  // constructor injection preferred
        this.usrRepo = usrRepo;
    }

    public List<User> getAllUsers() {
        return usrRepo.findAll();
    }

    public User saveUser(User user) {
        return usrRepo.save(user);
    }

}
