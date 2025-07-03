// Package and Imports
package org.pexamax.acheron;

import java.time.Instant;

public class Request {

	private Long requestID; 

	Long requesterID;
	Long requesteeID;
	Instant requestTimestamp;

	public Request(Long requester, Long requestee) {
		this.setRequesterID(requester);
		this.setRequesteeID(requestee);
		this.setRequestTimestamp(Instant.now());
	}

}
