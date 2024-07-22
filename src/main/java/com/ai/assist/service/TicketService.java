package com.ai.assist.service;

import com.ai.assist.dto.TicketDto;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    Ticket findById(Long id);

    List<Ticket> findByUserId(Long userId);

    List<Ticket> findByAssignedTo(Long assignedToId);

    Ticket create(TicketDto ticket);

    void delete(Long id);

}
