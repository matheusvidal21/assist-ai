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
public class TicketDto {

    private Long id;

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Issue is required")
    private String issue;

    private String status;

    private Long assignedTo;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

}
