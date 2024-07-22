package com.ai.assist.service;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.dto.response.MessageResponse;

import java.util.List;

public interface MessageService {

    List<MessageResponse> findAll();

    MessageResponse findById(Long id);

    List<MessageResponse> findByTicket(Long ticketId);

    List<MessageResponse> findByFromUser(Long fromUserId);

    List<MessageResponse> findByToUser(Long toUserId);

    MessageResponse create(MessageDto message);

    void delete(Long id);

}
