package com.fase3.techchallenge.fiap.usecase.exception;

public class BussinessErrorException extends RuntimeException {
    public BussinessErrorException(String message) {
        super(message);
    }
    public BussinessErrorException() {

    }
}

