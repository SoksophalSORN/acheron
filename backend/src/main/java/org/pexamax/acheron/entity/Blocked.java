// Package and Imports
package org.pexamax.acheron.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Blocked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockID; // Auto-generated and auto-incremented by JPA

    private UUID blockerID; 
    private UUID blockedID;
    private Instant blockedTimestamp;

    // Default constructor - required by JPA
    public Blocked() {}

    public Blocked(UUID blockerID, UUID blockedID) {
        this.setBlockerID(blockerID);
        this.setBlockedID(blockedID);
        this.setBlockedTimestamp(Instant.now());
    }

    public Long getBlockID() { return this.blockID; }
    public void setBlockID(Long blockID) { this.blockID = blockID; }

    public UUID getBlockerID() { return this.blockerID; }
    public void setBlockerID(UUID blockerID) { this.blockerID = blockerID; }

    public UUID getBlockedID() { return this.blockedID; }
    public void setBlockedID(UUID blockedID) { this.blockedID = blockedID; }

    public Instant getBlockedTimestamp() { return this.blockedTimestamp; }
    public void setBlockedTimestamp(Instant blockedTimestamp) { this.blockedTimestamp = blockedTimestamp;}
}

