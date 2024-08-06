package com.ai.assist.service.impl;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.dto.TicketDto;
import com.ai.assist.dto.response.TicketResponse;
import com.ai.assist.exception.BadRequestException;
import com.ai.assist.exception.NotFoundException;
import com.ai.assist.mapper.TicketMapper;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.model.enums.TicketStatus;
import com.ai.assist.repository.TicketRepository;
import com.ai.assist.repository.UserRepository;
import com.ai.assist.service.EmailService;
import com.ai.assist.service.TicketService;
import com.ai.assist.utils.EmailUtil;
import com.ai.assist.utils.TemplatesHtml;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

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
        if (ticket.getStatus() == null || ticket.getStatus().isEmpty()){
            ticket.setStatus(TicketStatus.OPEN.getValue());
        }

        Ticket entity = null;
        User assignedTo = null;
        if (ticket.getAssignedTo() != null) {
            assignedTo = userRepository.findById(ticket.getAssignedTo()).orElseThrow(() -> new NotFoundException("Assigned user not found"));
            if (!assignedTo.isAdmin())
                throw new BadRequestException("Only admin users can assign tickets");

            sendMessage(ticket, user, assignedTo, true);
            entity = this.ticketRepository.save(TicketMapper.fromDtoToEntity(ticket, user, assignedTo));
        } else {
            sendMessage(ticket, user, assignedTo, false);
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

    private void sendMessage(TicketDto ticket, User user, User assignedTo, boolean existsAssignedTo) {
        Map<String, Object> variables = EmailUtil.buildEmailVariables(
                    "title", "New Ticket Notification",
                    "h1Title", "New Ticket Created",
                    "paragraph", "A new ticket has been created:",
                    "name", user.getName(),
                    "issue", ticket.getIssue(),
                    "created_by", user.getUsername(),
                    "assigned_to", assignedTo != null ? assignedTo.getUsername() : null,
                    "status", ticket.getStatus(),
                    "time", ticket.getCreatedAt().toString()
            );

        Map<String, Object> variablesAssignedTo = new HashMap<>();
        if (existsAssignedTo && assignedTo != null) {
            variablesAssignedTo = EmailUtil.buildEmailVariables(
                    "title", "Ticket Received Notification",
                    "h1Title", "New Ticket Received",
                    "paragraph", "You have received a new ticket:",
                    "name", user.getName(),
                    "issue", ticket.getIssue(),
                    "created_by", user.getUsername(),
                    "assigned_to", assignedTo.getUsername(),
                    "status", ticket.getStatus(),
                    "time", ticket.getCreatedAt().toString()
            );
        }

        try {
            if (existsAssignedTo && assignedTo != null) {
                emailService.sendHtmlEmail(assignedTo.getEmail(), TemplatesHtml.CREATE_TICKET.get(0), TemplatesHtml.CREATE_TICKET.get(1), variablesAssignedTo);
            }
            emailService.sendHtmlEmail(user.getEmail(), TemplatesHtml.CREATE_TICKET.get(0), TemplatesHtml.CREATE_TICKET.get(1), variables);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

}
