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

    public void unblockUser() {
        // Unblocking a user by removing them from the blockedUsers list
    }
}
