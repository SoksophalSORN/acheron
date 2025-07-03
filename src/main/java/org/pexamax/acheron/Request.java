// Package and Imports
package org.pexamax.acheron;

import java.time.Instant;
import java.util.ArrayList;

public class Request {

	private long requestID; 

	private String requesterID;
	private String requesteeID;
	private Instant requestTimestamp;

    private static ArrayList<Request> outgoingRequests = new ArrayList<>(5);
    private static ArrayList<Request> incomingRequests = new ArrayList<>(5);

    // Creating new request
	public Request(String requester, String requestee) {
        this.requesterID = requester;
        this.requesteeID = requestee;
        this.requestTimestamp = Instant.now();
	}

    // Retrieving requests from database
    public Request(long id, String requester, String requestee, Instant timestamp) {
        this.requestID = id;
        this.requesterID = requester;
        this.requesteeID = requestee;
        this.requestTimestamp = timestamp;
    }

    public String getRequesterID() { return requesterID; }

    public String getRequesteeID() { return requesteeID; }

    public Instant getRequestTimestamp() { return requestTimestamp; }

    public static void retrieveOutgoingRequests(String requesterID) {
        // retrieving outgoingRequests from database
    }

    public static void retrieveIncomingRequests(String requesteeID) {
        // retrieving incoming requests from database
    }

    public static void displayOutgoingRequests() {
        // displaying outgoing requests from the outgoingRequests array list
    }

    public static void displayIncomingRequests() {
        // displaying the incoming requests from the incoming requests array list
    }

}
