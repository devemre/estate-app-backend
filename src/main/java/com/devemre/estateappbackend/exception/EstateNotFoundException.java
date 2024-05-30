package com.devemre.estateappbackend.exception;

public class EstateNotFoundException extends RuntimeException {

    public EstateNotFoundException(String message) {
        super(message);
    }
}
