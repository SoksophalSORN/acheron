// Package and Imports
package org.pexamax.acheron.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
class Blocked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockID; // Auto-generated and auto-incremented by JPA

    private UUID blockerID; 
    private UUID blockedID;
    private Instant blockedTimestamp;

    public Blocked(UUID blockerID, UUID blockedID) {
        this.blockerID(blockerID);
        this.blockedID(blockedID);
        this.blockedTimestamp(Instant.now());
    }

    public Long blockID() { return this.blockID; }
    public void blockID(Long blockID) { this.blockID = blockID; }

    public UUID blockerID() { return this.blockerID; }
    public void blockerID(UUID blockerID) { this.blockerID = blockerID; }

    public UUID blockedID() { return this.blockedID; }
    public void blockedID(UUID blockedID) { this.blockedID = blockedID; }

    public Instant blockedTimestamp() { return this.blockedTimestamp; }
    public void blockedTimestamp(Instant blockedTimestamp) { this.blockedTimestamp = blockedTimestamp;}
}

