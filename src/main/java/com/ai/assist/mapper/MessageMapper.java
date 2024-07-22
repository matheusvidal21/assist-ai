package com.ai.assist.mapper;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.model.Message;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;

public class MessageMapper {

    private MessageMapper() {
    }

    public static Message fromDtoToEntity(MessageDto dto, User fromUser, User toUser, Ticket ticket){
        return Message.builder()
                .id(dto.getId())
                .fromUser(fromUser)
                .toUser(toUser)
                .ticket(ticket)
                .content(dto.getContent())
                .timestamp(dto.getTimestamp())
                .build();
    }

}
