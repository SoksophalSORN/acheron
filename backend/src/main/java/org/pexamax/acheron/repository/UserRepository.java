// Package and Imports
package org.pexamax.acheron.repository;

import org.pexamax.acheron.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  // Optional: custom query methods
}

