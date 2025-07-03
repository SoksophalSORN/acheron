package org.pexamax.acheron;

import java.time.Instant;

public class Blocked {

	private long blockID; 

	private Long blockerID;
	private Long blockedID;
	private Instant blockedTimestamp;

	public Blocked(Long blockerID, Long blockedID) {
        this.blockerID = blockerID;
        this.blockedID = blockedID;
        this.blockedTimestamp = Instant.now();
	}
}
