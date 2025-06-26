package org.pexamax.acheron.repository;

import java.util.Optional; // Null-safe container for optional values
import org.pexamax.acheron.entity.Blocked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedRepository extends JpaRepository<Blocked, Long> {
	// JPA auto-generates methods for basic CRUD operations
	// Custom query methods are written below

	// SELECT * FROM blocked WHERE blocker_id=<blockerId> AND blocked_id=<blockedId>;
	Optional<Blocked> findByBlockerIdAndBlockedId(
		Long blockerId,
		Long blockedId
	);
}
