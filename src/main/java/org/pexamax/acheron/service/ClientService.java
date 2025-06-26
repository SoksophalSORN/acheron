package org.pexamax.acheron.service;

import jakarta.transaction.Transactional;
import org.pexamax.acheron.entity.Client;
import org.pexamax.acheron.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database)
// or rolled back (reverted) if an error occurs
public class ClientService {

	private final ClientRepository clientRepo;

	// Constructor Injection
	public ClientService(ClientRepository clientRepo) {
		this.clientRepo = clientRepo;
	}

	public Client registerClient(Client client) {
		return clientRepo.save(client);
	}

	public void revokeClient(Long id) {
		clientRepo.deleteById(id);
	}

	public void revokeClientByClientToken(String clientToken) {
		clientRepo.deleteByClientToken(clientToken);
	}

	public void revokeClientBySessionToken(String sessionToken) {
		clientRepo.deleteBySessionToken(sessionToken);
	}

	public Client getClient(Long id) {
		return clientRepo.findById(id).orElse(null);
	}

	public Client getClientByClientToken(String clientToken) {
		return clientRepo.findByClientToken(clientToken).orElse(null);
	}

	public Client getClientBySessionToken(String sessionToken) {
		return clientRepo.findBySessionToken(sessionToken).orElse(null);
	}

	public Client getClients(Long userId) {
		return clientRepo.findByUserId(userId).orElse(null);
	}

	public boolean authenticateClient(
		Long clientId,
		Long userId,
		String clientToken,
		String sessionToken
	) {
		return clientRepo
			.findByFullIdentifier(clientId, userId, clientToken, sessionToken)
			.isPresent();
	}
}
