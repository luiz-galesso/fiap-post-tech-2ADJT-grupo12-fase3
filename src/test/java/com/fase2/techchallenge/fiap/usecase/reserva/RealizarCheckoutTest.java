package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
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

public class RealizarCheckoutTest {
    @Mock
    RealizarCheckout realizarCheckout;

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
    void deveExecutarCheckout() {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "CHECKOUT");

        when(realizarCheckout.execute(any(Long.class), any(String.class))).thenReturn(reserva);
        //Act
        var resultado = realizarCheckout.execute(idReserva, idCliente);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);

    }

    @Test
    void deveGerarExcecao_QuandoIdClienteNaoPertencerAReserva() {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Reserva não pertence ao cliente informado."))
                .when(realizarCheckout).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> realizarCheckout.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Reserva não pertence ao cliente informado.");

    }

    @Test
    void deveGerarExcecao_QuandoSituacaoForInvalida() {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Não foi possível realizar o checkout. Reserva em situação inválida."))
                .when(realizarCheckout).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> realizarCheckout.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi possível realizar o checkout. Reserva em situação inválida.");

    }
}
