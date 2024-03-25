package com.fase2.techchallenge.fiap.entity.comentario.gateway;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.comentario.Comentar;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckin;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckout;
import com.fase2.techchallenge.fiap.usecase.reserva.Reservar;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ComentarioGatewayIT {

    @Autowired
    ComentarioGateway comentarioGateway;

    @Autowired
    Comentar comentar;

    @Autowired
    Reservar reservar;

    @Autowired
    RealizarCheckin checkin;

    @Autowired
    RealizarCheckout checkout;

    @Test
    void devePermitirCriar(){
        //Arrange
        Comentario comentario = criarComentario();
        //Act
        var comentarioArmazenado = comentarioGateway.create(comentario);
        //Assert
        assertThat(comentarioArmazenado).isInstanceOf(Comentario.class);
        assertThat(comentarioArmazenado.getId()).isNotNull();
        assertThat(comentarioArmazenado.getTexto()).isEqualTo(comentario.getTexto());
        assertThat(comentarioArmazenado.getDataRegistro()).isEqualTo(comentario.getDataRegistro());
    }

    @Test
    void devePermitirAtualizar(){
        //Arrange
        Comentario comentario = criarComentario();
        comentario.setTexto("COMIDA EXCELENTE!");
        //Act
        var comentarioArmazenado = comentarioGateway.update(comentario);
        //Assert
        assertThat(comentarioArmazenado).isInstanceOf(Comentario.class);
        assertThat(comentarioArmazenado.getId()).isNotNull();
        assertThat(comentarioArmazenado.getId()).isEqualTo(comentario.getId());
        assertThat(comentarioArmazenado.getTexto()).isEqualTo(comentario.getTexto());
        assertThat(comentarioArmazenado.getDataRegistro()).isEqualTo(comentario.getDataRegistro());
    }

    @Test
    void devePermitirDeletar(){
        // Arrange
        Comentario comentario = criarComentario();
        var id = comentario.getId();
        // Act
        comentarioGateway.deleteById(id);
        var comentarioOptional = comentarioGateway.findById(id);
        // Assert
        assertThat(comentarioOptional)
                .isEmpty();
    }

    @Test
    void devePermitirBuscar_PeloID(){
        //Arrange
        Comentario comentario = criarComentario();
        //Act
        var comentarioArmazenado = comentarioGateway.findById(comentario.getId());
        //Assert
        assertThat(comentarioArmazenado).isInstanceOf(Optional.of(comentario).getClass());
    }

    public Comentario criarComentario() {
        LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth().getValue(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute());

        Reserva reserva = reservar.execute(new ReservaInsertDTO(1L,1L,"felipe.silveira@example.com", dataInicio, 1));
        checkin.execute(reserva.getId(), "felipe.silveira@example.com");
        checkout.execute(reserva.getId(), "felipe.silveira@example.com");
        return comentar.execute(new ComentarioInsertDTO(reserva.getId(),"COMIDA MUITO BOA!"));
    }
}