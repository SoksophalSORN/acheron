package org.pexamax.acheron.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.pexamax.acheron.entity.User;
import org.pexamax.acheron.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database)
// or rolled back (reverted) if an error occurs
public class UserService {

	private final UserRepository userRepo;

	// Constructor Injection; Injecting UserRepository dependency
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public User registerUser(User user) {
		return userRepo.save(user);
	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

	public User getUser(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}

	public boolean validateCredentials(String username, String password) {
		return userRepo
			.findByUsernameAndPassword(username, password)
			.isPresent();
	}
}
