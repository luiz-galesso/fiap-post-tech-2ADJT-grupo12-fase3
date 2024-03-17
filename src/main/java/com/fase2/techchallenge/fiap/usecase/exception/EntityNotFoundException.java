package com.fase2.techchallenge.fiap.usecase.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {

    }
}

