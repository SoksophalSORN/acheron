// Package and Imports
package org.pexamax.acheron.service;

import org.pexamax.acheron.entity.Message;
import org.pexamax.acheron.repository.MessageRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service // indicates that this class is a service component
@Transactional // ensures transactional integrity - every transaction will be committed (make permanent change to the database) 
               // or rolled back (reverted) if an error occurs
public class MessageService {
    private final MessageRepository msgRepo;

    // Constructor Injection
    public MessageService(MessageRepository msgRepo) {  
        this.msgRepo = msgRepo;
    }

    //---Business logic for message entity---//
}
