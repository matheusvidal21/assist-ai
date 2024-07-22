package com.ai.assist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private Long id;

    @NotBlank(message = "FromUserId is required")
    private Long fromUserId;

    @NotBlank(message = "ToUserId is required")
    private Long toUserId;

    @NotBlank(message = "Content is required")
    @Size(min = 1, message = "Content must be longer than 1 character")
    private String content;

    private Long ticketId;

    private LocalDateTime timestamp = LocalDateTime.now();

}
