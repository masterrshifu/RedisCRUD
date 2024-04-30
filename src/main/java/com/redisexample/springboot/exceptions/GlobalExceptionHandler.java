package com.redisexample.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchFieldFoundException.class)
    public ResponseEntity<String> handleCustomRedisException(NoSuchFieldFoundException ex) {
        String errorMessage = "Error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

    }

    @ExceptionHandler(NoSuchKeyFoundException.class)
    public ResponseEntity<String> handleCustomRedisExceptionForKeyNotFound(NoSuchKeyFoundException ex) {
        String errorMessage = "Error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
