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
        this.requesterID(requester);
        this.requesteeID(requestee);
        requestTimestamp(Instant.now());
    }

    public Long requestID() { return this.requestID; }
    public void requestID(Long requestID) { this.requestID = requestID; }

    public UUID requesterID() { return this.requesterID; }
    public void requesterID(UUID requesterID) { this.requesterID = requesterID; }

    public UUID requesteeID() { return this.requesteeID; }
    public void requesteeID(UUID requesteeID) { this.requesteeID = requesteeID; }

    public Instant requestTimestamp() { return this.requestTimestamp; }
    public void requestTimestamp(Instant requestTimestamp) { this.requestTimestamp = requestTimestamp; }

}
