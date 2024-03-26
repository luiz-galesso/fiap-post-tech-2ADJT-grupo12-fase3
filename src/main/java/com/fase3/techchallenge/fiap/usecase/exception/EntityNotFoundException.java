package com.fase3.techchallenge.fiap.usecase.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {

    }
}

