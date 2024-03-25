package com.fase2.techchallenge.fiap.infrastructure.comentario.utils;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;

import java.time.LocalDateTime;

public class ComentarioHelper {
    public static Comentario criarComentario(){
        {
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());

            return Comentario.builder()
                    .id(1L)
                    .reserva(ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(2), "CHECKOUT"))
                    .texto("Comida boa")
                    .dataRegistro(dataInicio).build();
        }
    }
}
