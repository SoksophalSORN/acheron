// Package and Imports
package org.pexamax.acheron.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestID; // Auto-generated and auto-incremented by JPA

	Long requesterID;
	Long requesteeID;
	Instant requestTimestamp;

	// Default constructor - required by JPA
	public Request() {}

	public Request(Long requester, Long requestee) {
		this.setRequesterID(requester);
		this.setRequesteeID(requestee);
		this.setRequestTimestamp(Instant.now());
	}

	public Long getRequestID() {
		return this.requestID;
	}

	public void setRequestID(Long requestID) {
		this.requestID = requestID;
	}

	public Long getRequesterID() {
		return this.requesterID;
	}

	public void setRequesterID(Long id) {
		requesterID = id;
	}

	public Long getRequesteeID() {
		return this.requesteeID;
	}

	public void setRequesteeID(Long id) {
		requesteeID = id;
	}

	public Instant getRequestTimestamp() {
		return this.requestTimestamp;
	}

	public void setRequestTimestamp(Instant requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}
}
