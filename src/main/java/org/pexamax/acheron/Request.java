package org.pexamax.acheron;

import java.util.ArrayList;
import java.time.Instant;

public class Request extends Connection {

    private static ArrayList<Request> outgoingRequests = new ArrayList<>(5);
    private static ArrayList<Request> incomingRequests = new ArrayList<>(5);

    // Creating new request
    public Request(long requester, long requestee) {
        super(requester, requestee);
    }

    // Retrieving requests from database
    public Request(long id, long requester, long requestee, Instant timestamp) {
        super(id, requester, requestee, timestamp);
    }

    public static void retrieveOutgoingRequests(long requesterID) {
        // retrieving outgoingRequests from database
    }

    public static void retrieveIncomingRequests(long requesteeID) {
        // retrieving incoming requests from database
    }

    public static void displayOutgoingRequests() {
        // displaying outgoing requests from the outgoingRequests array list
    }

    public static void displayIncomingRequests() {
        // displaying the incoming requests from the incoming requests array list
    }

    public void acceptRequest() {
        // logic to accept the current request
    }

    public void rejectRequest() {
        // logic to reject the current request
    }

    public void cancelRequest() {
        // logic to cancel the current request
    }

    public static boolean areUsersConnected(long user1ID, long user2ID) {
        // Query for the connections between user1 and user2 from request table
        // If a connection exists the table, return true
        // else:
        return false;
    }

    public String toString() {
        return super.toString(); 
    }

    public boolean equals(Object request) {
        return super.equals(request);
    }
}
