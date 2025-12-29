package com.moghoneim.roomily.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {

        HttpStatus status;
        String errorCode;

        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = "BAD_REQUEST";
        } else if (ex instanceof IllegalStateException) {
            status = HttpStatus.CONFLICT;
            errorCode = "CONFLICT";
        } else if (ex instanceof RuntimeException) {
            // e.g., not found
            status = HttpStatus.NOT_FOUND;
            errorCode = "NOT_FOUND";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorCode = "INTERNAL_SERVER_ERROR";
        }

        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "errorCode", errorCode,
                "message", ex.getMessage() != null ? ex.getMessage() : "Unexpected error"
        );

        return new ResponseEntity<>(body, status);
    }
}
