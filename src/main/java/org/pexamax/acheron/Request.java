// Package and Imports
package org.pexamax.acheron;

import java.util.ArrayList;
import java.time.Instant;

public class Request extends Connection{

    private static ArrayList<Request> outgoingRequests = new ArrayList<>(5);
    private static ArrayList<Request> incomingRequests = new ArrayList<>(5);

    // Creating new request
	public Request(long requester, long requestee) {
        this.initiatorID = requester;
        this.receiverID = requestee;
        this.initTimestamp = Instant.now();
	}

    // Retrieving requests from database
    public Request(long id, long requester, long requestee, Instant timestamp) {
        this.ID = id;
        this.initiatorID = requester;
        this.receiverID = requestee;
        this.initTimestamp = timestamp;
    }

    public long getRequesterID() { return this.initiatorID; }

    public long getRequesteeID() { return this.receiverID; }

    public Instant getRequestTimestamp() { return this.initTimestamp; }

    public static void retrieveOutgoingRequests(long requesterID) {
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
