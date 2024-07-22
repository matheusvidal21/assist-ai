package com.ai.assist.service.impl;

import com.ai.assist.exception.NotFoundException;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.repository.TicketRepository;
import com.ai.assist.repository.UserRepository;
import com.ai.assist.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long id) {
        return this.ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));
    }

    @Override
    public Ticket save(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @Override
    public void delete(Long id) {
        this.ticketRepository.deleteById(id);
    }

    public List<Ticket> findByUserId(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return this.ticketRepository.findByUser(user);
    }

}
