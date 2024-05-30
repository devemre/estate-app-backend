package com.devemre.estateappbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstateNotFoundException.class)
    public ResponseEntity<Object> handleEstateNotFoundException(EstateNotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }
}
