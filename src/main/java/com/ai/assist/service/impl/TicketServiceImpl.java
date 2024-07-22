package com.ai.assist.service.impl;

import com.ai.assist.dto.TicketDto;
import com.ai.assist.dto.response.TicketResponse;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.TicketMapper;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.model.enums.TicketStatus;
import com.ai.assist.repository.TicketRepository;
import com.ai.assist.repository.UserRepository;
import com.ai.assist.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TicketResponse> findAll() {
        return this.ticketRepository.findAll().stream().map(TicketMapper::fromEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public TicketResponse findById(Long id) {
        Ticket entity = this.ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));
        return TicketMapper.fromEntityToResponse(entity);
    }

    @Override
    public TicketResponse create(TicketDto ticket) {
        User user = userRepository.findById(ticket.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        Ticket entity = null;
        ticket.setStatus(TicketStatus.OPEN.getValue());
        if (ticket.getAssignedTo() != null) {
            User assignedTo = userRepository.findById(ticket.getAssignedTo()).orElseThrow(() -> new NotFoundException("Assigned user not found"));
            entity = this.ticketRepository.save(TicketMapper.fromDtoToEntity(ticket, user, assignedTo));
        } else {
            entity = this.ticketRepository.save(TicketMapper.fromDtoToEntity(ticket, user, null));
        }
        return TicketMapper.fromEntityToResponse(entity);
    }

    @Override
    public void delete(Long id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    public TicketResponse assignTo(Long ticketId, Long userAssignedId) {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));

        User user = this.userRepository.findById(userAssignedId).orElseThrow(() -> new NotFoundException("User not found"));

        ticket.setAssignedTo(user);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setStatus(TicketStatus.BACKLOG);
        Ticket entity = this.ticketRepository.save(ticket);
        return TicketMapper.fromEntityToResponse(entity);
    }

    @Override
    public List<TicketResponse> findByUserId(Long userId){
        return this.ticketRepository.findByUserId(userId).stream().map(TicketMapper::fromEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> findByAssignedTo(Long assignedToId){
        return this.ticketRepository.findByAssignedToId(assignedToId).stream().map(TicketMapper::fromEntityToResponse).collect(Collectors.toList());
    }

}
