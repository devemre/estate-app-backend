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

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<Object> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }

    @ExceptionHandler(RegisterNotSuccessfulException.class)
    public ResponseEntity<Object> handleRegisterNotSuccessfulException(RegisterNotSuccessfulException exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }
}
