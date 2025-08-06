package com.health.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException; // <-- IMPORT THIS
import org.springframework.web.bind.MethodArgumentNotValidException; // <-- AND THIS
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalExceptionHandler {

    // ** ADD THIS NEW METHOD TO HANDLE BAD JSON OR VALIDATION ERRORS **
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");

        if (ex instanceof MethodArgumentNotValidException) {
            // This handles validation errors (e.g., if you add @NotNull, @Size to your DTO)
            String errors = ((MethodArgumentNotValidException) ex).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            body.put("message", "Validation failed: " + errors);
        } else {
            // This handles malformed JSON
            body.put("message", "Request body is malformed or contains invalid data.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }

    // This handler catches "User already exists" or "Invalid role" errors.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
    
    // (Your other handlers for DataIntegrityViolationException and general Exception can remain here)
}