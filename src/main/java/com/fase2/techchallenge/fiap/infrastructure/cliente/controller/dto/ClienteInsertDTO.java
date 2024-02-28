package com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClienteInsertDTO {

    private Long idRestaurante;

    private Long idMesa;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFinal;
}
