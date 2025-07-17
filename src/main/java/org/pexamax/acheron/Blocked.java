package org.pexamax.acheron;

import java.time.Instant;

public class Blocked extends Connection {

	public Blocked(Long blockerID, Long blockedID) {
        this.initiatorID = blockerID;
        this.receiverID = blockedID;
        this.initTimestamp = Instant.now();
	}
}
