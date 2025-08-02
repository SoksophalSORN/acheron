// Package and Imports
package org.pexamax.acheron;

public interface Persistable {

    // Saves the current state of the object to persistent storage.
    // This method should handle any necessary serialization or database operations.
    void save();

    // Loads the state of the object from persistent storage.
    // This method should handle any necessary deserialization or database retrieval.
    void load();

    // Basic CRUD operations for the object
}

