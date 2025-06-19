// Package and Imports
package org.pexamax.acheron.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestID; // Auto-generated and auto-incremented by JPA

    UUID requesterID;
    UUID requesteeID;
    Instant requestTimestamp;

    public Requests(UUID requester, UUID requestee) {
        this.setRequesterID(requester);
        this.setRequesteeID(requestee);
        this.setRequestTimestamp(Instant.now());
    }

    public Long getRequestID() { return this.requestID; }
    public void setRequestID(Long requestID) { this.requestID = requestID; }

    public UUID getRequesterID() { return this.requesterID; }
    public void setRequesterID(UUID requesterID) { this.requesterID = requesterID; }

    public UUID getRequesteeID() { return this.requesteeID; }
    public void setRequesteeID(UUID requesteeID) { this.requesteeID = requesteeID; }

    public Instant getRequestTimestamp() { return this.requestTimestamp; }
    public void setRequestTimestamp(Instant requestTimestamp) { this.requestTimestamp = requestTimestamp; }

}
