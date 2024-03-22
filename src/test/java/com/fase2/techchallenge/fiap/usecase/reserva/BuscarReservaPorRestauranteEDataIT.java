package com.fase2.techchallenge.fiap.usecase.reserva;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class BuscarReservaPorRestauranteEDataIT {

    @Autowired
    BuscarReservaPorRestauranteEData buscarReservaPorRestauranteEData;

    @Test
    void devePermitirBuscarReservaPorRestauranteEData() {
        //Arrange
        Long idRestaurante = 1L;
        LocalDate localDate = LocalDate.now();
        //Act
        var resultado = buscarReservaPorRestauranteEData.execute(idRestaurante, localDate);
        //Assert
        assertThat(resultado).isNotNull();
    }
}
