package com.ai.assist.service;

import com.ai.assist.dto.TicketDto;
import com.ai.assist.dto.response.TicketResponse;

import java.util.List;

public interface TicketService {

    List<TicketResponse> findAll();

    TicketResponse findById(Long id);

    List<TicketResponse> findByUserId(Long userId);

    List<TicketResponse> findByAssignedTo(Long assignedToId);

    TicketResponse create(TicketDto ticket);

    void delete(Long id);

    TicketResponse assignTo(Long ticketId, Long userAssignedId);

}
