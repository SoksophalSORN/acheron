package org.pexamax.acheron.repository;

import java.util.Optional; // Null-safe container for optional values
import org.pexamax.acheron.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
	// JPA auto-generates methods for basic CRUD operations
	// Custom query methods are written below

	void deleteByConversationId(Long conversationId); // DELETE FROM message WHERE conversation_id=<conversationId>;
	Message[] findByConversationId(Long conversationId); // SELECT * FROM message WHERE conversation_id=<conversationId>;
}
