package org.pexamax.acheron;

import java.time.Instant;

public class Connection {
    protected long ID;
    protected long initiatorID;
    protected long receiverID;
    protected Instant initTimestamp;

    public long getInitiatorID() {
        return initiatorID;
    }

    public long getReceiverID() {
        return receiverID;
    }

    public long getInitTimestamp() {
        return initTimestamp;
    }
}
