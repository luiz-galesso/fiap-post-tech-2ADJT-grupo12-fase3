package com.fase2.techchallenge.fiap.entity.restaurante.exception;

public class RestauranteNotFoundException extends Exception {
    public RestauranteNotFoundException() {
        super("Restaurante não encontrado.");
    }
}

