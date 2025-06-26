// Package and Imports
package org.pexamax.acheron.repository;

import java.util.Optional; // Null-safe container for optional values
import org.pexamax.acheron.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository
	extends JpaRepository<Conversation, Long> {
	// JPA auto-generates methods for basic CRUD operations
	// Custom query methods are written below

	// Delete all messages associated with this user's conversations first then,
	// DELETE FROM conversation WHERE user_id1="<userId>" OR user_id2="<userId>";
	@Query("DELETE FROM Conversation c WHERE c.user1Id = ?1 OR c.user2Id = ?1")
	void deleteByUserId(Long userId);

	// SELECT * FROM conversation WHERE (user_id1="<user1Id>" AND user_id2="<user2Id>")
	//                                   OR (user_id1="<user2Id" AND user_id2="<user1Id");
	@Query(
		"SELECT c FROM Conversation c WHERE (c.user1Id = ?1 AND c.user2Id = ?2) OR (c.user1Id = ?2 AND c.user2Id = ?1)"
	)
	Conversation findByUserIds(Long userId1, Long userId2);

	// SELECT * FROM conversation WHERE user_id1="<userId>" OR user_id2="<userId>";
	@Query(
		"SELECT c FROM Conversation c WHERE c.user1Id = ?1 OR c.user2Id = ?1"
	)
	Conversation[] findByUserId(Long userId);
}
