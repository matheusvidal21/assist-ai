package com.ai.assist.mapper;

import com.ai.assist.dto.MessageDto;
import com.ai.assist.dto.response.MessageResponse;
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

    public static MessageResponse fromEntityToResponse(Message message){
        MessageResponse response = MessageResponse.builder()
                .id(message.getId())
                .fromUserId(message.getFromUser().getId())
                .toUserId(message.getToUser().getId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();

        if (message.getTicket() != null) {
            response.setTicketId(message.getTicket().getId());
        }
        return response;
    }

}
