// Package and Imports
package org.pexamax.acheron.repository;

import org.pexamax.acheron.entity.Blocked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // Null-safe container for optional values

public interface BlockedRepository extends JpaRepository<Blocked, Long> {
    // JPA auto-generates methods for basic CRUD operations
    // Custom query methods are written below
}

