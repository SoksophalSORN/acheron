// Package and Imports
package org.pexamax.acheron.service;

import org.pexamax.acheron.entity.Client;
import org.pexamax.acheron.repository.ClientRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database) 
               // or rolled back (reverted) if an error occurs
public class ClientService {
    private final ClientRepository clientRepo;
    // Business logic for client entity

    // public clientService(ClientRepository clientRepo) {  // constructor injection preferred
    //     this.clientRepo = clientRepo;
    // }
}
