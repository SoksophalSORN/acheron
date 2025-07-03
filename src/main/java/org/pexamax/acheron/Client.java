package org.pexamax.acheron;

import java.time.Instant;

public class Client {

	private long clientID; 

	private String sessionToken;
	private String clientToken;
	private Instant sessionTokenExpiry;
	private Instant clientTokenExpiry;

	public Client(
		String sessionToken,
		String clientToken,
		Instant sessionTokenExpiry,
		Instant clientTokenExpiry
	) {
        this.clientID = 0; // Default value, can be set later
        this.sessionToken = sessionToken;
        this.clientToken = clientToken;
        this.sessionTokenExpiry = sessionTokenExpiry;
        this.clientTokenExpiry = clientTokenExpiry;
	}
}
