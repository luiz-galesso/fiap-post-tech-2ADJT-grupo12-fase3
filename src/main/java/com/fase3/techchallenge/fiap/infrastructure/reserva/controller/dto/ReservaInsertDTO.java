package com.fase3.techchallenge.fiap.infrastructure.reserva.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservaInsertDTO {

    private Long idRestaurante;

    private Long idMesa;

    private String idCliente;

    private LocalDateTime dataHoraInicio;

    private Integer quantidadeHoras;
}
