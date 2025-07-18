package org.pexamax.acheron;

import java.time.Instant;

public class Connection {
    private long ID;
    private long initiatorID;
    private long receiverID;
    private Instant initTimestamp;

    // Create new record
    public Connection(long initiatorID, long receiverID) {
        this.initiatorID = initiatorID;
        this.receiverID = receiverID;
        this.initTimestamp = Instant.now();
    }

    // Retrieve existing record
    public Connection(long id, long initiatorID, long receiverID, Instant timestamp) {
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
}
