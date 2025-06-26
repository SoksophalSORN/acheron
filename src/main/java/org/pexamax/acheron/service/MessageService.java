package org.pexamax.acheron.service;

import jakarta.transaction.Transactional;
import org.pexamax.acheron.entity.Message;
import org.pexamax.acheron.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database)
// or rolled back (reverted) if an error occurs
public class MessageService {

	private final MessageRepository msgRepo;

	// Constructor Injection
	public MessageService(MessageRepository msgRepo) {
		this.msgRepo = msgRepo;
	}

	public Message sendMessage(Message message) {
		return msgRepo.save(message);
	}

	public void deleteMessage(Long id) {
		msgRepo.deleteById(id);
	}

	public void deleteMessages(Long conversationId) {
		msgRepo.deleteByConversationId(conversationId);
	}

	public Message getMessage(Long id) {
		return msgRepo.findById(id).orElse(null);
	}

	public Message[] getMessages(Long conversationId) {
		return msgRepo.findByConversationId(conversationId);
	}
}
