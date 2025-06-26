// Package and Imports
package org.pexamax.acheron.service;

import jakarta.transaction.Transactional;
import org.pexamax.acheron.entity.Blocked;
import org.pexamax.acheron.repository.BlockedRepository;
import org.springframework.stereotype.Service;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database)
// or rolled back (reverted) if an error occurs
public class BlockedService {

	private final BlockedRepository blockedRepo;

	// Constructor Injection
	public BlockedService(BlockedRepository blockedRepo) {
		this.blockedRepo = blockedRepo;
	}

	public Blocked blockUser(Blocked block) {
		return blockedRepo.save(block);
	}

	public void unblockUser(Blocked block) {
		blockedRepo.delete(block);
	}

	public Blocked getBlockedRecord(Long id) {
		return blockedRepo.findById(id).orElse(null);
	}

	public Blocked getBlockedUser(Long blockerId, Long blockedId) {
		return blockedRepo.findByBlockerIdAndBlockedId(blockerId, blockedId);
	}
}
