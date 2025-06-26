package org.pexamax.acheron.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long clientID; // Auto-generated and auto-incremented by JPA

	private String sessionToken;
	private String clientToken;
	private Instant sessionTokenExpiry;
	private Instant clientTokenExpiry;

	// Default constructor - required by JPA
	public Client() {}

	public Client(
		String sessionToken,
		String clientToken,
		Instant sessionTokenExpiry,
		Instant clientTokenExpiry
	) {
		this.setSessionToken(sessionToken);
		this.setClientToken(clientToken);
		this.setSessionTokenExpiry(sessionTokenExpiry);
		this.setClientTokenExpiry(clientTokenExpiry);
	}

	// Getters & Setters
	public String getSessionToken() {
		return this.sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public Long getClientID() {
		return this.clientID;
	}

	public void setClientID(Long id) {
		clientID = id;
	}

	public String getClientToken() {
		return this.clientToken;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public Instant getSessionTokenExpiry() {
		return this.sessionTokenExpiry;
	}

	public void setSessionTokenExpiry(Instant sessionTokenExpiry) {
		this.sessionTokenExpiry = sessionTokenExpiry;
	}

	public Instant getClientTokenExpiry() {
		return this.clientTokenExpiry;
	}

	public void setClientTokenExpiry(Instant clientTokenExpiry) {
		this.clientTokenExpiry = clientTokenExpiry;
	}
}
