package com.fase2.techchallenge.fiap.usecase.reserva;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BuscarReservaPorRestauranteEDataTest {
    @Mock
    BuscarReservaPorRestauranteEData buscarReservaPorRestauranteEData;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    //melhorar
    @Test
    void devePermitirBuscarReservaPorRestauranteEData() {
        //Arrange
        Long idRestaurante = 1L;
        LocalDate localDate = LocalDate.now();

        when(buscarReservaPorRestauranteEData.execute(any(Long.class), any(LocalDate.class))).thenReturn(new ArrayList<>());
        //Act
        var resultado = buscarReservaPorRestauranteEData.execute(idRestaurante, localDate);
        //Assert
        assertThat(resultado).isNotNull();
    }
}
