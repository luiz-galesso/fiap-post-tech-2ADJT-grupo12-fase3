package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class RealizarCheckoutIT {

    @Autowired
    RealizarCheckout realizarCheckout;

    @Autowired
    Reservar reservar;

    @Autowired
    RealizarCheckin realizarCheckin;

    @Test
    void deveRealizarCheckout() {
        //Arrange
        String idCliente = "maria.santos@example.com";

        //criando reserva
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
                1L,
                1L,
                idCliente,
                dataInicio,
                2
        );
        Reserva reserva = reservar.execute(reservaInsertDTO);
        //checkin
        realizarCheckin.execute(reserva.getId(), idCliente);
        //Act
        var resultado = realizarCheckout.execute(reserva.getId(), idCliente);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);

    }

    @Test
    void deveGerarExcecao_QuandoIdClienteNaoPertencerAReserva() {
        //Arrange
        String idCliente = "maria.santos@example.com";
        String idClienteErr = "joao.silva@example.com";

        //criando reserva
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
                1L,
                1L,
                idCliente,
                dataInicio,
                2
        );
        Reserva reserva = reservar.execute(reservaInsertDTO);
        //checkin
        realizarCheckin.execute(reserva.getId(), idCliente);

        //Assert
        assertThatThrownBy(() -> realizarCheckout.execute(reserva.getId(), idClienteErr))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Reserva não pertence ao cliente informado.");

    }

    @Test
    void deveGerarExcecao_QuandoSituacaoForInvalida() {
        //Arrange
        String idCliente = "maria.santos@example.com";

        //criando reserva
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
                1L,
                1L,
                idCliente,
                dataInicio,
                2
        );
        Reserva reserva = reservar.execute(reservaInsertDTO);
        //checkin
        realizarCheckin.execute(reserva.getId(), idCliente);
        //checkout
        realizarCheckout.execute(reserva.getId(), idCliente);
        //Assert
        assertThatThrownBy(() -> realizarCheckout.execute(reserva.getId(), idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi possível realizar o checkout. Reserva em situação inválida.");

    }
}
