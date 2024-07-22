package com.ai.assist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {

    private Long id;

    @NotBlank(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Issue is required")
    private String issue;

    private String status;

    private Long assignedTo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
