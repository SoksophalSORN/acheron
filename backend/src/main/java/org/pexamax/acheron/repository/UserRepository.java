// Package and Imports
package org.pexamax.acheron.repository;

import org.pexamax.acheron.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Null-safe container for optional values

public interface UserRepository extends JpaRepository<User, Long> {
    // JPA auto-generates methods for basic CRUD operations
    // Custom query methods are written below
    Optional<User> findByEmail(String email); // Query: SELECT * FROM users WHERE email = "<email>"

}

