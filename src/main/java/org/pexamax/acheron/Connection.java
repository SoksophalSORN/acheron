package org.pexamax.acheron;

import java.time.Instant;

public class Connection {
    private long ID;
    private long initiatorID;
    private long receiverID;
    private Instant initTimestamp;

    // Create new record
    protected Connection(long initiatorID, long receiverID) {
        this.initiatorID = initiatorID;
        this.receiverID = receiverID;
        this.initTimestamp = Instant.now();
    }

    // Retrieve existing record
    protected Connection(long id, long initiatorID, long receiverID, Instant timestamp) {
        this.ID = id;
        this.initiatorID = initiatorID;
        this.receiverID = receiverID;
        this.initTimestamp = timestamp;
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " Properties:\n Instance ID: " + ID + "\n Initiator ID: " + initiatorID + "\n Receiver ID: " + receiverID + "\n Initiation Timestamp: " + initTimestamp + "\n";
    }

    @Override
    public boolean equals(Object conn) {
        if (conn instanceof Connection) {
            return this.initiatorID == ((Connection)conn).initiatorID &&
                this.receiverID == ((Connection)conn).receiverID && 
                this.initTimestamp.equals(((Connection)conn).initTimestamp);
        }
        return false;
    }
}
