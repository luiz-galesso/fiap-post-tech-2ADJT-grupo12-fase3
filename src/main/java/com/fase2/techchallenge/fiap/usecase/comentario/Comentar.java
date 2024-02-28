package com.fase2.techchallenge.fiap.usecase.comentario;

import com.fase2.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Comentar {
    private final ComentarioGateway comentarioGateway;
    private final ReservaGateway reservaGateway;
    public Comentar(ComentarioGateway comentarioGateway, ReservaGateway reservaGateway) {
        this.comentarioGateway = comentarioGateway;
        this.reservaGateway = reservaGateway;
    }

    public Comentario execute(ComentarioInsertDTO comentarioInsertDTO) {

        Reserva reserva = reservaGateway.findById(comentarioInsertDTO.getIdReserva()).orElseThrow();

        Comentario comentario =
                new Comentario(reserva,
                        comentarioInsertDTO.getComentario(),
                        LocalDateTime.now()
                );

        return this.comentarioGateway.create(comentario);
    }
}
