package org.pexamax.acheron.repository;

import java.util.Optional; // Null-safe container for optional values
import org.pexamax.acheron.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	// JPA auto-generates methods for basic CRUD operations
	// Custom query methods are written below

	User findByUsername(String username); // SELECT * FROM users WHERE username = "<username>";
	User findByEmail(String email); // SELECT * FROM users WHERE email = "<email>";
	User findByUsernameAndPassword(String username, String password); // SELECT * FROM users WHERE username = "<username>" AND password = "<password>";
	User findByEmailAndPassword(String email, String password); // SELECT * FROM users WHERE email = "<email>" AND password = "<password>";
}
