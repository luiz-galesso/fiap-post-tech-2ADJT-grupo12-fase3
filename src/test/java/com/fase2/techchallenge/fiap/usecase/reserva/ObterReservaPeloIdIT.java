package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ObterReservaPeloIdIT {

    @Autowired
    ObterReservaPeloId obterReservaPeloId;

    @Autowired
    Reservar reservar;

    @Test
    void devePermitirObterReservaPeloId() {
        //Arrange
        String idCliente = "maria.santos@example.com";

        //criando reserva
        //utilizando data atual para fazer o checkin
        LocalDateTime dataInicio = now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        Reserva reserva = reservar.execute(reservaInsertDTO);
        //Act
        var resultado = obterReservaPeloId.execute(reserva.getId());
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);
        assertThat(resultado.getId()).isEqualTo(reserva.getId());
    }

    //NoSuchElementException

    @Test
    void deveGerarExcecao_QuandoNaoEncontrarReserva() {
        //Arrange
        Long idReserva = 2L;
        String idCliente = "maria.santos@example.com";

        //criando reserva
        LocalDateTime dataInicio = of(2024, 3, 21, 18, 30);
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, idCliente, dataInicio, 2);
        reservar.execute(reservaInsertDTO);

        //Assert
        assertThatThrownBy(() -> obterReservaPeloId.execute(idReserva))
                .isInstanceOf(NoSuchElementException.class);
    }
}
