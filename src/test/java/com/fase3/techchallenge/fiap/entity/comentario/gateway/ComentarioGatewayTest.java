package com.fase3.techchallenge.fiap.entity.comentario.gateway;

import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.infrastructure.comentario.utils.ComentarioHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        Comentario comentario = ComentarioHelper.criarComentario();
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
        Comentario comentario = ComentarioHelper.criarComentario();
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
        Comentario comentario = ComentarioHelper.criarComentario();
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
        Comentario comentario = ComentarioHelper.criarComentario();
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
}
