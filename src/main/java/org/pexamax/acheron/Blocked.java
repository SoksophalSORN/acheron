package org.pexamax.acheron.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Blocked {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long blockID; // Auto-generated and auto-incremented by JPA

	private Long blockerID;
	private Long blockedID;
	private Instant blockedTimestamp;

	// Default constructor - required by JPA
	public Blocked() {}

	public Blocked(Long blockerID, Long blockedID) {
		this.setBlockerID(blockerID);
		this.setBlockedID(blockedID);
		this.setBlockedTimestamp(Instant.now());
	}

	public Long getBlockID() {
		return this.blockID;
	}

	public void setBlockID(Long blockID) {
		this.blockID = blockID;
	}

	public Long getBlockerID() {
		return this.blockerID;
	}

	public void setBlockerID(Long id) {
		blockerID = id;
	}

	public Long getBlockedID() {
		return this.blockedID;
	}

	public void setBlockedID(Long id) {
		blockedID = id;
	}

	public Instant getBlockedTimestamp() {
		return this.blockedTimestamp;
	}

	public void setBlockedTimestamp(Instant blockedTimestamp) {
		this.blockedTimestamp = blockedTimestamp;
	}
}
