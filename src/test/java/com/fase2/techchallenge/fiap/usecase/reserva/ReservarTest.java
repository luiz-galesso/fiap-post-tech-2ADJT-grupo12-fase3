package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservarTest
{
    @Mock
    Reservar reservar;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveExecutarReserva() {

        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
            1L,
                1L,
                "joao.silva@example.com",
                dataInicio,
                2
        );
        Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(reservaInsertDTO.getQuantidadeHoras()), "RESERVADO");
        when(reservar.execute(any(ReservaInsertDTO.class))).thenReturn(reserva);
        //Act
        var resultado = reservar.execute(reservaInsertDTO);
        //Assert
        assertThat(resultado).isInstanceOf(Reserva.class);
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getDataHoraFinal()).isEqualTo(reserva.getDataHoraFinal());
    }

    @Test
    void deveGerarExcecao_QuandoMesaNaoAtiva() {

        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
                1L,
                2L,
                "joao.silva@example.com",
                dataInicio,
                2
        );
        Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(reservaInsertDTO.getQuantidadeHoras()), "RESERVADO");
        when(reservar.execute(any(ReservaInsertDTO.class))).thenReturn(reserva);

        //Assert
        assertThatThrownBy(() -> reservar.execute(reservaInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Mesa não está disponível para ser reservada.");
    }
}
