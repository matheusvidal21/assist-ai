package com.ai.assist.mapper;

import com.ai.assist.dto.TicketDto;
import com.ai.assist.dto.response.TicketResponse;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.model.enums.TicketStatus;

public class TicketMapper {

    private TicketMapper() {
    }

    public static Ticket fromDtoToEntity(TicketDto dto, User user, User assignedTo){
        Ticket entity = Ticket.builder()
                .id(dto.getId())
                .user(user)
                .issue(dto.getIssue())
                .status(TicketStatus.fromValue(dto.getStatus()))
                .assignedTo(assignedTo)
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        if (assignedTo != null){
            entity.setAssignedTo(assignedTo);
        }
        return entity;
    }

    public static TicketResponse fromEntityToResponse(Ticket ticket){
        var response = TicketResponse.builder()
                .id(ticket.getId())
                .userId(ticket.getUser().getId())
                .issue(ticket.getIssue())
                .status(ticket.getStatus().getValue())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();

        if (ticket.getAssignedTo() != null){
            response.setAssignedTo(ticket.getAssignedTo().getId());
        }
        return response;
    }

}
