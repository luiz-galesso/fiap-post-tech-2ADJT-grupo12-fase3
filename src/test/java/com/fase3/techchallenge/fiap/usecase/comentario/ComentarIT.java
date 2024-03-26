package com.fase3.techchallenge.fiap.usecase.comentario;


import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.comentario.utils.ComentarioHelper;
import com.fase3.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase3.techchallenge.fiap.usecase.exception.EntityNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ComentarIT {

    @Autowired
    Comentar comentar;

    @Autowired
    Reservar reservar;

    @Autowired
    RealizarCheckin realizarCheckin;

    @Autowired
    RealizarCheckout realizarCheckout;

    @Test
    void devePermitirRegistrarComentario() {
        LocalDateTime dataInicio = LocalDateTime.now();
        String idCliente = "felipe.albuquerque@example.com";

        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);
        realizarCheckin.execute(reserva.getId(), idCliente);
        realizarCheckout.execute(reserva.getId(), idCliente);
        Comentario comentario = ComentarioHelper.criarComentario();

        //Act
        Comentario comentarioAlterado = comentar.execute(new ComentarioInsertDTO(reserva.getId(), comentario.getTexto()));

        //Assert
        assertThat(comentarioAlterado).isNotNull();
        assertThat(comentarioAlterado.getId()).isEqualTo(comentario.getId());
        assertThat(comentarioAlterado.getTexto()).isEqualTo(comentario.getTexto());
        assertThat(comentarioAlterado.getReserva().getId()).isEqualTo(comentarioAlterado.getReserva().getId());

    }

    @Test
    void deveGerarExcecao_QuandoCadastrarComentario_ReservaNaoCadastrada() {
        Comentario comentario = ComentarioHelper.criarComentario();
        ComentarioInsertDTO comentarioInsertDTO = new ComentarioInsertDTO(236l, comentario.getTexto());

        assertThatThrownBy(() -> comentar.execute(comentarioInsertDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Reserva não encontrada.");
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarComentario_ReservaInvalida() {
        LocalDateTime dataInicio = LocalDateTime.now();
        String idCliente = "felipe.albuquerque@example.com";
        Comentario comentario = ComentarioHelper.criarComentario();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);

        assertThatThrownBy(() -> comentar.execute(new ComentarioInsertDTO(reserva.getId(), comentario.getTexto())))
                .isInstanceOf(BussinessErrorException.class).hasMessage("Não é possível comentar. Reserva em situação inválida.");
    }
}
