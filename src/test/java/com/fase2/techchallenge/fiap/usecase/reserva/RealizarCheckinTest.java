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

public class RealizarCheckinTest {
    @Mock
    RealizarCheckin realizarCheckin;

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
    void deveRealizarCheckin() {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "CHECKIN");

        when(realizarCheckin.execute(any(Long.class), any(String.class))).thenReturn(reserva);
        //Act
        var resultado = realizarCheckin.execute(idReserva, idCliente);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Reserva.class);
        assertThat(resultado.getSituacao()).isEqualTo("CHECKIN");
    }

    @Test
    void deveGerarExecao_QuandoReservaNaoPertencerAoCliente () {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Reserva não pertence ao cliente informado."))
                .when(realizarCheckin).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> realizarCheckin.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Reserva não pertence ao cliente informado.");
    }

    @Test
    void deveGerarExecao_QuandoReservaInativa () {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Não foi possível realizar o checkout. Reserva em situação inválida."))
                .when(realizarCheckin).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> realizarCheckin.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi possível realizar o checkout. Reserva em situação inválida.");
    }

    @Test
    void deveGerarExecao_QuandoDataAtualForMenorQueOInicioDaReserva () {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Aguarde o horário de ínicio da reserva para realizar o checkin."))
                .when(realizarCheckin).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> realizarCheckin.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Aguarde o horário de ínicio da reserva para realizar o checkin.");
    }

    @Test
    void deveGerarExecao_QuandoDataAtualForMaiorQueADataFinalDaReserva () {
        //Arrange
        Long idReserva = 1L;
        String idCliente = "maria.santos@example.com";

        //Act
        doThrow(new BussinessErrorException("Não é mais possível realizar o checkin, o horário final da reserva foi ultrapassado."))
                .when(realizarCheckin).execute(any(Long.class), any(String.class));

        //Assert
        assertThatThrownBy(() -> realizarCheckin.execute(idReserva, idCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não é mais possível realizar o checkin, o horário final da reserva foi ultrapassado.");
    }

}
