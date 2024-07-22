package com.ai.assist.service.impl;

import com.ai.assist.dto.TicketDto;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.TicketMapper;
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
    public Ticket create(TicketDto ticket) {
        User user = userRepository.findById(ticket.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        User assignedTo = userRepository.findById(ticket.getAssignedTo()).orElseThrow(() -> new NotFoundException("Assigned user not found"));
        return this.ticketRepository.save(TicketMapper.fromDtoToEntity(ticket, user, assignedTo));
    }

    @Override
    public void delete(Long id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findByUserId(Long userId){
        return this.ticketRepository.findByUserId(userId);
    }

    @Override
    public List<Ticket> findByAssignedTo(Long assignedToId){
        return this.ticketRepository.findByAssignedToId(assignedToId);
    }

}
