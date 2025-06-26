package org.pexamax.acheron.service;

import jakarta.transaction.Transactional;
import org.pexamax.acheron.entity.Request;
import org.pexamax.acheron.repository.RequestRepository;
import org.springframework.stereotype.Service;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database)
// or rolled back (reverted) if an error occurs
public class RequestService {

	private final RequestRepository requestRepo;

	// Constructor Injection
	public RequestsService(RequestRepository requestRepo) {
		this.requestRepo = requestRepo;
	}

	public Request createRequest(Request request) {
		return requestRepo.save(request);
	}

	public void cancelRequest(Request request) {
		requestRepo.delete(request);
	}

	public Request getRequest(Long id) {
		return requestRepo.findById(id).orElse(null);
	}

	public Request getRequestByUserIds(Long requesterId, Long requesteeId) {
		return requestRepo.findByRequesterIdAndRequesteeId(
			requesterId,
			requesteeId
		);
	}
}
