package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ReservarIT
{
    @Autowired
    Reservar reservar;

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
        //Act
        var resultado = reservar.execute(reservaInsertDTO);
        //Assert
        assertThat(resultado).isInstanceOf(Reserva.class);
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getDataHoraFinal()).isEqualTo(reservaInsertDTO.getDataHoraInicio().plusHours(reservaInsertDTO.getQuantidadeHoras()));
    }
}
