package org.pexamax.acheron.service;

import jakarta.transaction.Transactional;
import org.pexamax.acheron.entity.Conversation;
import org.pexamax.acheron.repository.ConversationRepository;
import org.springframework.stereotype.Service;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database)
// or rolled back (reverted) if an error occurs
public class ConversationService {

	private final ConversationRepository convoRepo;

	// Constructor Injection
	public ConversationService(ConversationRepository convoRepo) {
		this.convoRepo = convoRepo;
	}

	public Conversation createConversation(Conversation conversation) {
		return convoRepo.save(conversation);
	}

	public void deleteConversation(Long conversationId) {
		convoRepo.deleteById(conversationId);
	}

	public void deleteByUserId(Long userId) {
		convoRepo.deleteByUserId(userId);
	}

	public Conversation findByUserIds(Long user1Id, Long user2Id) {
		return convoRepo.findByUserIds(user1Id, user2Id);
	}

	public Conversation[] findByUserId(Long userId) {
		return convoRepo.findByUserId(userId);
	}
}
