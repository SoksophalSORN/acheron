// Package and Imports
package org.pexamax.acheron.service;

import org.pexamax.acheron.entity.Requests;
import org.pexamax.acheron.repository.RequestsRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database) 
               // or rolled back (reverted) if an error occurs
public class RequestsService {
    private final RequestsRepository requestsRepo;
    // Business logic for Requests entity

    // public requestsService(RequestsRepository requestsRepo) {  // constructor injection preferred
    //     this.requestsRepo = requestsRepo;
    // }
}
