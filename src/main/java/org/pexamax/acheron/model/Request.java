package org.pexamax.acheron.model;

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

    public Conversation acceptRequest() {
        // Delete the request from the request
        deleteConnection(); // and then delete the request
        return new Conversation(getInitiatorID(), getReceiverID());
    }

    // Function to use for rejecting and cancelling a request
    public void deleteRequest() {
        deleteConnection();
    }

    public static boolean areUsersConnected(long user1ID, long user2ID) {
        // Query for the connections between user1 and user2 from request table
        // If a connection exists the table, return true
        // else:
        return false;
    }

    @Override
    protected boolean deleteConnection() {
        // Used to accept, reject and cancel user request for initiating conversation
        // Delete the request from the database
        // And then the object from the respective array list
        return false;
    }

    public String toString() {
        return super.toString();
    }

    public boolean equals(Object request) {
        return super.equals(request);
    }
}
