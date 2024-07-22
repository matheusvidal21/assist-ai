package com.ai.assist.mapper;

import com.ai.assist.dto.TicketDto;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.model.enums.TicketStatus;

public class TicketMapper {

    private TicketMapper() {
    }

    public static Ticket fromDtoToEntity(TicketDto dto, User user){
        return Ticket.builder()
                .id(dto.getId())
                .user(user)
                .issue(dto.getIssue())
                .status(TicketStatus.fromValue(dto.getStatus()))
                .assignedTo(dto.getAssignedTo())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }


}
