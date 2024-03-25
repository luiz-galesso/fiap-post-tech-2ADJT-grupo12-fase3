package com.fase2.techchallenge.fiap.infrastructure.mesa.controller.dto;

import lombok.Data;

@Data
public class MesaInsertDTO {

    private Long idRestaurante;

    private Integer capacidade;

    private String tipo;

}