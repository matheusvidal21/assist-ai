package com.ai.assist.exception;

import com.ai.assist.dto.response.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {

    private int status;

    private HttpStatus httpStatus;

    private String message;

    private List<ValidationError> errors;

}
