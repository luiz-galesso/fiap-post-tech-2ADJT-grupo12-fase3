package com.fase2.techchallenge.fiap.entity.comentario.gateway;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.stereotype.Component;

public class ComentarioGatewayTest {

    @Mock
    ComentarioGateway comentarioGateway;

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
    void devePermitirCriar() {
        //Arrange
        Comentario comentario = criarComentario();
        when(comentarioGateway.create(any(Comentario.class))).thenReturn(comentario);
        //Act
        var comentarioArmazenado = comentarioGateway.create(comentario);
        //Assert
        verify(comentarioGateway, times(1)).create(comentario);
        assertThat(comentarioArmazenado).isInstanceOf(Comentario.class);
        assertThat(comentarioArmazenado.getId()).isNotNull();
        assertThat(comentarioArmazenado)
                .extracting(Comentario::getId)
                .isEqualTo(comentario.getId());
        assertThat(comentarioArmazenado.getTexto()).isEqualTo(comentario.getTexto());
        assertThat(comentarioArmazenado.getDataRegistro()).isEqualTo(comentario.getDataRegistro());
    }

    @Test
    void devePermitirAtualizar() {
        //Arrange
        Comentario comentario = criarComentario();
        comentario.setTexto("COMIDA EXCELENTE!");
        when(comentarioGateway.update(any(Comentario.class))).thenReturn(comentario);
        //Act
        var comentarioArmazenado = comentarioGateway.update(comentario);
        //Assert
        verify(comentarioGateway, times(1)).update(comentario);
        assertThat(comentarioArmazenado).isInstanceOf(Comentario.class);
        assertThat(comentarioArmazenado.getId()).isNotNull();
        assertThat(comentarioArmazenado.getId()).isEqualTo(comentario.getId());
        assertThat(comentarioArmazenado.getTexto()).isEqualTo(comentario.getTexto());
        assertThat(comentarioArmazenado.getDataRegistro()).isEqualTo(comentario.getDataRegistro());
    }

    @Test
    void devePermitirDeletar() {
        // Arrange
        Comentario comentario = criarComentario();
        var id = comentario.getId();
        doNothing().when(comentarioGateway).deleteById(id);
        // Act
        comentarioGateway.deleteById(id);
        var comentarioOptional = comentarioGateway.findById(id);
        // Assert
        assertThat(comentarioOptional)
                .isEmpty();
        // Assert
        verify(comentarioGateway, times(1)).deleteById(id);
    }

    @Test
    void devePermitirBuscar_PeloID() {
        //Arrange
        Comentario comentario = criarComentario();
        when(comentarioGateway.findById(any(Long.class)))
                .thenReturn(Optional.of(comentario));
        //Act
        var comentarioArmazenado = comentarioGateway.findById(comentario.getId());
        //Assert
        verify(comentarioGateway, times(1)).findById(comentario.getId());
        assertThat(comentarioArmazenado).isInstanceOf(Optional.of(comentario).getClass());
        assertThat(comentarioArmazenado)
                .isPresent()
                .containsSame(comentario);
        assertThat(comentarioArmazenado)
                .isPresent()
                .containsSame(comentario);
        comentarioArmazenado.ifPresent(mensagemArmazenada -> {
            assertThat(mensagemArmazenada.getId())
                    .isEqualTo(comentario.getId());
        });
    }

    public Comentario criarComentario() {
        LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth().getValue(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute());

        return Comentario.builder()
                .id(1L)
                .reserva(ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(2), "CHECKOUT"))
                .texto("Comida boa")
                .dataRegistro(dataInicio).build();
    }
}
