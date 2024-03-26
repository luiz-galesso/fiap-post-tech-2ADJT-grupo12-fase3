package com.fase3.techchallenge.fiap.usecase.comentario;


import com.fase3.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.comentario.utils.ComentarioHelper;
import com.fase3.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase3.techchallenge.fiap.usecase.reserva.RealizarCheckin;
import com.fase3.techchallenge.fiap.usecase.reserva.RealizarCheckout;
import com.fase3.techchallenge.fiap.usecase.reserva.Reservar;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ApagarComentarioIT {
    @Autowired
    Comentar comentar;

    @Autowired
    ApagarComentario apagarComentario;

    @Autowired
    RealizarCheckin realizarCheckin;

    @Autowired
    RealizarCheckout realizarCheckout;

    @Autowired
    ComentarioGateway comentarioGateway;

    @Autowired
    Reservar reservar;

    @Test
    void devePermitirApagarComentario() {
        LocalDateTime dataInicio = LocalDateTime.now();
        String idCliente = "felipe.albuquerque@example.com";

        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);
        realizarCheckin.execute(reserva.getId(), idCliente);
        realizarCheckout.execute(reserva.getId(), idCliente);
        Comentario comentario = ComentarioHelper.criarComentario();
        Comentario comentarioSalvo = comentar.execute(new ComentarioInsertDTO(reserva.getId(), comentario.getTexto()));

        //Act
        apagarComentario.execute(comentarioSalvo.getId());
        var comentarioOptional = comentarioGateway.findById(comentario.getId());

        // Assert
        assertThat(comentarioOptional)
                .isEmpty();
    }
}
