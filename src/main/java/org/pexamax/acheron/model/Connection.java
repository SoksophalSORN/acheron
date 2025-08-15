package org.pexamax.acheron.model;

import java.time.Instant;

public abstract class Connection {
    private long ID;
    private long initiatorID;
    private long receiverID;
    private Instant initTimestamp;

    // Create new record
    protected Connection(long initiatorID, long receiverID) {
        setInitiatorID(initiatorID);
        setReceiverID(receiverID);
        setInitTimestamp(Instant.now());
    }

    // Retrieve existing record
    protected Connection(long id, long initiatorID, long receiverID, Instant timestamp) {
        setID(id);
        setInitiatorID(initiatorID);
        setReceiverID(receiverID);
        setInitTimestamp(timestamp);
    }

    protected long getID() {
        return ID;
    }

    protected long getInitiatorID() {
        return initiatorID;
    }

    protected long getReceiverID() {
        return receiverID;
    }

    protected Instant getInitTimestamp() {
        return initTimestamp;
    }

    private void setID(long id) {
        if (id < 1)
            throw new IllegalArgumentException("ID must be a positive number.");
        else
            this.ID = id;
    }

    private void setInitiatorID(long initiatorID) {
        if (initiatorID < 1)
            throw new IllegalArgumentException("Initiator ID must be a positive number.");
        else
            this.initiatorID = initiatorID;
    }

    private void setReceiverID(long receiverID) {
        if (receiverID < 1)
            throw new IllegalArgumentException("Receiver ID must be a positive number.");
        else
            this.receiverID = receiverID;
    }

    private void setInitTimestamp(Instant initTimestamp) {
        if (initTimestamp == null) {
            throw new IllegalArgumentException("Initiation timestamp cannot be null.");
        } else if (initTimestamp.isAfter(Instant.now())) {
            throw new IllegalArgumentException("Initiation timestamp cannot be in the future.");
        } else {
            this.initTimestamp = initTimestamp;
        }
    }

    public static boolean areUsersConnected(long user1ID, long user2ID) {
        // Query for the connections between user1 and user2 from conversation and
        // request tables
        // If a connection exists in either one of the table, return true
        // else:
        return false;
    }

    protected abstract boolean deleteConnection();
    // Delete the connection from the specified table from the database
    // based on the initiatorID and receiverID.
    // Table name can be either "conversation", "request", or "blocked".
    // Then, remove the connection from the local collection.

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " Properties:\n Instance ID: " + ID + "\n Initiator ID: " + initiatorID
                + "\n Receiver ID: " + receiverID + "\n Initiation Timestamp: " + initTimestamp + "\n";
    }

    @Override
    public boolean equals(Object conn) {
        if (conn instanceof Connection) {
            return this.initiatorID == ((Connection) conn).initiatorID &&
                    this.receiverID == ((Connection) conn).receiverID &&
                    this.initTimestamp.equals(((Connection) conn).initTimestamp);
        }
        return false;
    }
}
