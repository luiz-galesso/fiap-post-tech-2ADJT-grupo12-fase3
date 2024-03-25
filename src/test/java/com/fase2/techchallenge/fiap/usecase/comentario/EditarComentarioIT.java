package com.fase2.techchallenge.fiap.usecase.comentario;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckin;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckout;
import com.fase2.techchallenge.fiap.usecase.reserva.Reservar;
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
        var comentarioAlterado = editarComentario.execute(comentario.getId(), "COMIDA JÓIA!");

        //Assert
        assertThat(comentarioAlterado).isNotNull();
        assertThat(comentarioAlterado.getId()).isEqualTo(comentario.getId());
        assertThat(comentarioAlterado.getTexto()).isEqualTo(comentario.getTexto());
        assertThat(comentarioAlterado.getReserva().getId()).isEqualTo(comentarioAlterado.getReserva().getId());
    }
}