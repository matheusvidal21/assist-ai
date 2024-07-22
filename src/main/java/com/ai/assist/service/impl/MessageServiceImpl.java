
package com.ai.assist.service.impl;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.exception.BadRequestException;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.MessageMapper;
import com.ai.assist.model.Message;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.repository.MessageRepository;
import com.ai.assist.service.MessageService;
import com.ai.assist.service.TicketService;
import com.ai.assist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Override
    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        return this.messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    @Override
    public Message create(MessageDto message) {
        if (message.getId() != null) {
            throw new BadRequestException("Message id must be null");
        }

        User fromUser = this.userService.findById(message.getFromUserId());
        User toUser = this.userService.findById(message.getToUserId());
        Ticket ticket = this.ticketService.findById(message.getTicketId());


        return this.messageRepository.save(MessageMapper.fromDtoToEntity(message, fromUser, toUser, ticket));
    }

    @Override
    public void delete(Long id) {
        this.messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findByTicket(Long ticketId) {
        return this.messageRepository.findByTicketId(ticketId);
    }

    @Override
    public List<Message> findByFromUser(Long fromUserId) {
        return this.messageRepository.findByFromUserId(fromUserId);
    }

    @Override
    public List<Message> findByToUser(Long toUserId) {
        return this.messageRepository.findByToUserId(toUserId);
    }
}
