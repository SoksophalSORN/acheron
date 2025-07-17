package org.pexamax.acheron;

import java.time.Instant;
import java.util.ArrayList;

public class Blocked extends Connection {

        private static ArrayList<Blocked> blockedUsers = new ArrayList<>(5);

        // Creating a new blocked user
        public Blocked(long blocker, long blocked) {
                this.initiatorID = blocker;
                this.receiverID = blocked;
                this.initTimestamp = Instant.now();
        }

        // Retrieving blocked users from database
        public Blocked(long id, long blocker, long blocked, Instant timestamp) {
                this.ID = id;
                this.initiatorID = blocker;
                this.receiverID = blocked;
                this.initTimestamp = timestamp;
        }

        public static void retrieveBlockedUsers(long blockerID) {
                // retrieving blocked users from database
        }

        public static void displayBlockedUsers() {
                // displaying the blocked users from the blockedUsers array list
        }
}
