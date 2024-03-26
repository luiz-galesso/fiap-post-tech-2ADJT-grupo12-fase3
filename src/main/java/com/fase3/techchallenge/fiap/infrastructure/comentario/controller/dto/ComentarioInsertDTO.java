package com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ComentarioInsertDTO {

    private Long idReserva;

    private String texto;

}
