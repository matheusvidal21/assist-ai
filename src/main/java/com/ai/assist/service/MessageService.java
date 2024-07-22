package com.ai.assist.service;

import com.ai.assist.model.Message;
import com.ai.assist.model.Ticket;

import java.util.List;

public interface MessageService {

    List<Message> findAll();

    Message findById(Long id);

    Message save(Message message);

    void delete(Long id);

}
