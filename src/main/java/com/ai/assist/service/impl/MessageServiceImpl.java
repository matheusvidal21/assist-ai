
package com.ai.assist.service.impl;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.dto.response.MessageResponse;
import com.ai.assist.exception.BadRequestException;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.MessageMapper;
import com.ai.assist.model.Message;
import com.ai.assist.model.Role;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.repository.MessageRepository;
import com.ai.assist.repository.TicketRepository;
import com.ai.assist.repository.UserRepository;
import com.ai.assist.service.EmailService;
import com.ai.assist.service.MessageService;
import com.ai.assist.service.TicketService;
import com.ai.assist.service.UserService;
import com.ai.assist.utils.EmailUtil;
import com.ai.assist.utils.TemplatesHtml;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<MessageResponse> findAll() {
        return this.messageRepository.findAll().stream().map(MessageMapper::fromEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public MessageResponse findById(Long id) {
        Message entity = this.messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
        return MessageMapper.fromEntityToResponse(entity);
    }

    @Override
    @Transactional
    public MessageResponse create(MessageDto message) {
        if (message.getId() != null) {
            throw new BadRequestException("Message id must be null");
        }

        User fromUser = this.userRepository.findById(message.getFromUserId()).orElseThrow(() -> new NotFoundException("From user not found"));
        User toUser = this.userRepository.findById(message.getToUserId()).orElseThrow(() -> new NotFoundException("To user not found"));

        Ticket ticket = null;
        String ticketName = null;
        if (message.getTicketId() != null){
            ticket = this.ticketRepository.findById(message.getTicketId()).orElseThrow(() -> new NotFoundException("Ticket not found"));
            ticketName = ticket.getIssue();
        }

        sendMessage(message, fromUser, toUser, ticketName);


        Message entity = this.messageRepository.save(MessageMapper.fromDtoToEntity(message, fromUser, toUser, ticket));
        return MessageMapper.fromEntityToResponse(entity);
    }

    @Override
    public void delete(Long id) {
        this.messageRepository.deleteById(id);
    }

    @Override
    public List<MessageResponse> findByTicket(Long ticketId) {
        return this.messageRepository.findByTicketId(ticketId).stream().map(MessageMapper::fromEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<MessageResponse> findByFromUser(Long fromUserId) {
        return this.messageRepository.findByFromUserId(fromUserId).stream().map(MessageMapper::fromEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<MessageResponse> findByToUser(Long toUserId) {
        return this.messageRepository.findByToUserId(toUserId).stream().map(MessageMapper::fromEntityToResponse).collect(Collectors.toList());
    }

    private void sendMessage(MessageDto message, User fromUser, User toUser, String ticketName) {
        Map<String, Object> variables = EmailUtil.buildEmailVariables(
                "fromUser", fromUser.getUsername(),
                "toUser", toUser.getUsername(),
                "content", message.getContent(),
                "time", message.getTimestamp().toString(),
                "ticketName", ticketName
        );
        try {
            emailService.sendHtmlEmail(toUser.getEmail(), TemplatesHtml.MESSAGE_RECEIVED.get(0), TemplatesHtml.MESSAGE_RECEIVED.get(1), variables);
            emailService.sendHtmlEmail(fromUser.getEmail(), TemplatesHtml.MESSAGE_SENT.get(0), TemplatesHtml.MESSAGE_SENT.get(1), variables);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
