package com.ai.assist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignTicketDto {

    @NotNull(message = "Ticket id is required")
    private Long ticketId;

    @NotNull(message = "User id to assign is required")
    private Long assignedTo;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
