package com.fase2.techchallenge.fiap.infrastructure.avaliacao.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AvaliacaoInsertDTO {

    private Long idReserva;

    private Integer valor;

}
