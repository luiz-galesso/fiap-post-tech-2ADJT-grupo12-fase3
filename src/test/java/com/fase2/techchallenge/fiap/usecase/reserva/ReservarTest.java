package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

        //Act
        doThrow(new BussinessErrorException("Mesa não está disponível para ser reservada.")).when(reservar).execute(any(ReservaInsertDTO.class));

        //Assert
        assertThatThrownBy(() -> reservar.execute(reservaInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Mesa não está disponível para ser reservada.");
    }

    @Test
    void deveGerarExcecao_QuandoOClienteNaoAtivo() {
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
                1L,
                2L,
                "joao.silva@example.com",
                dataInicio,
                2
        );

        //Act
        doThrow(new BussinessErrorException("Cliente está inativo e não pode realizar uma reserva.")).when(reservar).execute(any(ReservaInsertDTO.class));

        //Assert
        assertThatThrownBy(() -> reservar.execute(reservaInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente está inativo e não pode realizar uma reserva.");
    }

    @Test
    void deveGerarExcecao_QuandoExisteReserva() {
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(
                1L,
                2L,
                "maria.santos@example.com",
                dataInicio,
                2
        );

        //Act
        doThrow(new BussinessErrorException("Já existe reserva para a mesa e horário escolhidos.")).when(reservar).execute(any(ReservaInsertDTO.class));

        //Assert
        assertThatThrownBy(() -> reservar.execute(reservaInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Já existe reserva para a mesa e horário escolhidos.");
    }
}
