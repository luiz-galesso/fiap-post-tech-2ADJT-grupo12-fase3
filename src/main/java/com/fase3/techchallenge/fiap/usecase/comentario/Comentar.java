package com.fase3.techchallenge.fiap.usecase.comentario;

import com.fase3.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase3.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Comentar {
    private final ComentarioGateway comentarioGateway;
    private final ReservaGateway reservaGateway;
    public Comentar(ComentarioGateway comentarioGateway, ReservaGateway reservaGateway) {
        this.comentarioGateway = comentarioGateway;
        this.reservaGateway = reservaGateway;
    }

    public Comentario execute(ComentarioInsertDTO comentarioInsertDTO) {

        Reserva reserva = reservaGateway.findById(comentarioInsertDTO.getIdReserva()).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada."));
        if (!reserva.getSituacao().equals("CHECKOUT")) {
            throw new BussinessErrorException("Não é possível comentar. Reserva em situação inválida.");
        }
        Comentario comentario =
                new Comentario(reserva,
                        comentarioInsertDTO.getTexto(),
                        LocalDateTime.now()
                );

        return this.comentarioGateway.create(comentario);
    }
}
