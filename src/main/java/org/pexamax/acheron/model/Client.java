package org.pexamax.acheron.model;

import java.time.Instant;

public class Client {

	private long clientID;

	private long userID;
	private String sessionToken;
	private String clientToken;
	private Instant sessionTokenExpiry;
	private Instant clientTokenExpiry;

	public Client(
			long userID,
			String sessionToken,
			String clientToken,
			Instant sessionTokenExpiry,
			Instant clientTokenExpiry) {
		this.clientID = 0; // auto-increment
		this.userID = userID;
		this.sessionToken = sessionToken;
		this.clientToken = clientToken;
		this.sessionTokenExpiry = sessionTokenExpiry;
		this.clientTokenExpiry = clientTokenExpiry;
	}

}
