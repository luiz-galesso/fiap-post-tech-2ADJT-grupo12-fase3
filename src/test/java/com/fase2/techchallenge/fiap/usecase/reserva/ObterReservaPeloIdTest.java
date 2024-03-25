package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ObterReservaPeloIdTest {

    @Mock
    ObterReservaPeloId obterReservaPeloId;

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
    void devePermitirObterReservaPeloId() {
        //Arrange
        Long idReserva = 1L;
        LocalDateTime dataInicio = LocalDateTime.now();
        Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "RESERVADO");
        when(obterReservaPeloId.execute(idReserva)).thenReturn(reserva);
        //Act
        var resultado = obterReservaPeloId.execute(idReserva);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);
        assertThat(resultado.getId()).isEqualTo(idReserva);
    }

    //NoSuchElementException

    @Test
    void deveGerarExcecao_QuandoNaoEncontrarReserva() {
        //Arrange
        Long idReserva = 1L;
        //Act
        doThrow(new NoSuchElementException())
                .when(obterReservaPeloId).execute(idReserva);
        //Assert
        assertThatThrownBy(() -> obterReservaPeloId.execute(idReserva))
                .isInstanceOf(NoSuchElementException.class);
    }
}
