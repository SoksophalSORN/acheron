package org.pexamax.acheron.repository;

import java.util.Optional; // Null-safe container for optional values
import org.pexamax.acheron.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
	// JPA auto-generates methods for basic CRUD operations
	// Custom query methods are written below

	// SELECT * FROM request WHERE requester_id=<requesterId> AND requestee_id=<requesteeId>;
	Optional<Request> findByRequesterIdAndRequesteeId(
		Long requesterId,
		Long requesteeId
	);
}
