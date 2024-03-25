package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class BuscarReservasAtivasPorClienteIT {
    @Autowired
    BuscarReservasAtivasPorCliente buscarReservasAtivasPorCliente;

    @Autowired
    Reservar reservar;

    @Test
    void devePermitirBuscarReservasAtivasPorCliente() {
        //Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO1 = new ReservaInsertDTO(
                1L,
                1L,
                "maria.santos@example.com",
                localDateTime,
                1
        );
        ReservaInsertDTO reservaInsertDTO2 = new ReservaInsertDTO(
                1L,
                3L,
                "maria.santos@example.com",
                localDateTime,
                1
        );
        ReservaInsertDTO reservaInsertDTO3 = new ReservaInsertDTO(
                1L,
                3L,
                "maria.santos@example.com",
                localDateTime.plusHours(4),
                1
        );
        reservar.execute(reservaInsertDTO1);
        reservar.execute(reservaInsertDTO2);
        reservar.execute(reservaInsertDTO3);

        Pageable pageable = Pageable.unpaged();
        //Act
        var resultado = buscarReservasAtivasPorCliente.execute(reservaInsertDTO1.getIdCliente(), pageable);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Page.class);
    }
}
