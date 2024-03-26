package com.fase3.techchallenge.fiap.usecase.comentario;

import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase3.techchallenge.fiap.usecase.reserva.RealizarCheckin;
import com.fase3.techchallenge.fiap.usecase.reserva.RealizarCheckout;
import com.fase3.techchallenge.fiap.usecase.reserva.Reservar;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class EditarComentarioIT {

    @Autowired
    Reservar reservar;

    @Autowired
    RealizarCheckin realizarCheckin;

    @Autowired
    RealizarCheckout realizarCheckout;

    @Autowired
    EditarComentario editarComentario;

    @Autowired
    Comentar comentar;

    @Test
    void devePermitirEditarComentario() {
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.now();
        String idCliente = "felipe.albuquerque@example.com";
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);
        realizarCheckin.execute(reserva.getId(), idCliente);
        realizarCheckout.execute(reserva.getId(), idCliente);
        Comentario comentario = comentar.execute(new ComentarioInsertDTO(reserva.getId(), "Comida joia"));

        //Act
        var comentarioAlterado = editarComentario.execute(comentario.getId(), "COMIDA EXCELENTE!");

        //Assert
        Assertions.assertThat(comentarioAlterado).isNotNull();
        Assertions.assertThat(comentarioAlterado.getId()).isEqualTo(comentario.getId());
        Assertions.assertThat(comentarioAlterado.getTexto()).isEqualTo(comentario.getTexto());
        Assertions.assertThat(comentarioAlterado.getReserva().getId()).isEqualTo(comentarioAlterado.getReserva().getId());
    }
}