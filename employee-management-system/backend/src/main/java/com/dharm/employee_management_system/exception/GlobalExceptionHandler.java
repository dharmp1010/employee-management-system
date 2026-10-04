package com.dharm.employee_management_system.exception;

import com.dharm.employee_management_system.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles all our custom exceptions (ResourceNotFoundException, DuplicateResourceException, InvalidCredentialsException)
    // since they all extend RuntimeException, one method catches them all
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(400, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }

    // Handles @Valid validation failures (blank fields, invalid email format, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getFieldError().getDefaultMessage();
        ErrorResponse error = new ErrorResponse(400, message, LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }
}