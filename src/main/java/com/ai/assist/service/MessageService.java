package com.ai.assist.service;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.model.Message;
import com.ai.assist.model.Ticket;

import java.util.List;

public interface MessageService {

    List<Message> findAll();

    Message findById(Long id);

    List<Message> findByTicket(Long ticketId);

    List<Message> findByFromUser(Long fromUserId);

    List<Message> findByToUser(Long toUserId);

    Message create(MessageDto message);

    void delete(Long id);


}
