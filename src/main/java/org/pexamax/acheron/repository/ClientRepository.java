package org.pexamax.acheron.repository;

import java.util.Optional; // Null-safe container for optional values
import org.pexamax.acheron.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
	// JPA auto-generates methods for basic CRUD operations
	// Custom query methods are written below

	void deleteByClientToken(String clientToken); // DELETE FROM client WHERE client_token="<clientToken>";
	void deleteBySessionToken(String sessionToken); // DELETE FROM client WHERE session_token="<sessionToken>";
	Client findByClientToken(String clientToken); // SELECT * FROM client WHERE client_token="<clientToken>";
	Client findBySessionToken(String sessionToken); // SELECT * FROM client WHERE session_token="<sessionToken>";
	Client[] findByUserID(Long userId); // SELECT * FROM client WHERE user_id=<userId>;

	@Query(
		"SELECT c FROM Client c WHERE c.id = ?1 AND c.user.id = ?2 AND c.clientToken = ?3 AND c.sessionToken = ?4"
	)
	Client findByFullIdentifier(
		Long clientId,
		Long userId,
		String clientToken,
		String sessionToken
	);
}
