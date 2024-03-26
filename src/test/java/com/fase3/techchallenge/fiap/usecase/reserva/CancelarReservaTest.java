package com.fase3.techchallenge.fiap.usecase.reserva;

import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class CancelarReservaTest {

    @Mock
    CancelarReserva cancelarReserva;

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
    void devePermitirCancelarReserva() {
        //Arrange
        Long idReserva = 1L;
        LocalDateTime dataInicio = LocalDateTime.now();
        Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "CANCELADA");
        when(cancelarReserva.execute(reserva.getId(), reserva.getCliente().getEmail())).thenReturn(reserva);
        //Act
        var resultado = cancelarReserva.execute(reserva.getId(), reserva.getCliente().getEmail());
        //Assert
        Assertions.assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);
        Assertions.assertThat(resultado.getId()).isEqualTo(idReserva);
        Assertions.assertThat(resultado.getSituacao()).isEqualTo("CANCELADA");
    }

    @Test
    void deveGerarExcecao_QuandoReservaNaoPertencerAoCliente() {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        Mockito.doThrow(new BussinessErrorException("Reserva não pertence ao cliente informado."))
                .when(cancelarReserva).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> cancelarReserva.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Reserva não pertence ao cliente informado.");
    }

    @Test
    void deveGerarExcecao_QuandoSituacaoInvalida() {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Não foi possível realizar o checkout. Reserva em situação inválida."))
                .when(cancelarReserva).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> cancelarReserva.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi possível realizar o checkout. Reserva em situação inválida.");
    }
}
