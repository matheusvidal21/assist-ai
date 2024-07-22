package com.ai.assist.service;

import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    Ticket findById(Long id);

    Ticket save(Ticket ticket);

    void delete(Long id);

}
