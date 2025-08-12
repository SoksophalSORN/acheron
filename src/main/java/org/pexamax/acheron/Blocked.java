package org.pexamax.acheron;

import java.time.Instant;
import java.util.ArrayList;

public class Blocked extends Connection {

    private static ArrayList<Blocked> blockedUsers = new ArrayList<>(5);

    // Creating a new blocked user
    public Blocked(long blocker, long blocked) {
        super(blocker, blocked);
    }

    // Retrieving blocked users from database
    public Blocked(long id, long blocker, long blocked, Instant timestamp) {
        super(id, blocker, blocked, timestamp);
    }

    public static void retrieveBlockedUsers(long blockerID) {
        // retrieving blocked users from database
    }

    public static void displayBlockedUsers() {
        // displaying the blocked users from the blockedUsers array list
    }

    // Unblock a user
    public void unblockUser() {
        deleteConnection(); 
    }

    protected boolean deleteConnection() {
        // Delete blocked user from the blocked table
        // based on the initiatorID and receiverID.
        // Then, remove the connection from the local collection.
        // return true if succeed, false if failed
        return false;
    }


    public static boolean areUsersConnected(long user1ID, long user2ID) {
        // Query for the connections between user1 and user2 from blocked table
        // If a connection exists the table, return true
        // else:
        return false;
    }

}
