package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class CancelarReservaIT {

    @Autowired
    CancelarReserva cancelarReserva;

    @Autowired
    Reservar reservar;

    @Test
    void devePermitirCancelarReserva() {
        //Arrange
        String idCliente = "maria.santos@example.com";

        //criando reserva
        //utilizando data atual para fazer o checkin
        LocalDateTime dataInicio = now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);

        //Act
        var resultado = cancelarReserva.execute(reserva.getId(), idCliente);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);
    }

    @Test
    void deveGerarExcecao_QuandoReservaNaoPertencerAoCliente() {
        //Arrange
        String idCliente = "maria.santos@example.com";
        String idClienteErr = "maria.souza@example.com";

        //criando reserva
        LocalDateTime dataInicio = of(2024, 3, 21, 18, 30);
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);

        //Assert
        assertThatThrownBy(() -> cancelarReserva.execute(reserva.getId(), idClienteErr))
                .isInstanceOf(BussinessErrorException.class).hasMessage("Reserva não pertence ao cliente informado.");
    }

    @Test
    void deveGerarExcecao_QuandoSituacaoInvalida() {
        //Arrange
        String idCliente = "maria.santos@example.com";

        //criando reserva
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);

        //realizando checking
        cancelarReserva.execute(reserva.getId(), idCliente);

        //Assert
        assertThatThrownBy(() -> cancelarReserva.execute(reserva.getId(), idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi possível cancelar a reserva. Reserva em situação inválida.");
    }
}
