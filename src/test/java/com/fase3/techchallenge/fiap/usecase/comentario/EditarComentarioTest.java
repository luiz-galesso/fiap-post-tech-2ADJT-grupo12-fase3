package com.fase3.techchallenge.fiap.usecase.comentario;

import com.fase3.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
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


public class EditarComentarioTest {

    EditarComentario editarComentario;
    @Mock
    ComentarioGateway comentarioGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        editarComentario = new EditarComentario(comentarioGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirEditarComentario() {
        Comentario comentario = ComentarioHelper.criarComentario();
        when(comentarioGateway.findById(comentario.getId()))
                .thenReturn(Optional.of(comentario));
        when(comentarioGateway.update(any(Comentario.class)))
                .thenReturn(comentario);

        ComentarioInsertDTO comentarioInsertDTO = new ComentarioInsertDTO(
                comentario.getReserva().getId(), "Comida OK!"
        );

        var comentarioAlterado = editarComentario.execute(comentario.getId(),
                comentarioInsertDTO.getTexto());

        assertThat(comentarioAlterado).isInstanceOf(Comentario.class).isNotNull();
        assertThat(comentarioAlterado).isEqualTo(comentario);
    }
}