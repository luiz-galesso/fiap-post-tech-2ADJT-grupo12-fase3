package com.fase2.techchallenge.fiap.usecase.comentario;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.comentario.utils.ComentarioHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ComentarTest {

    @Mock
    Comentar comentar;

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
    void devePermitirRegistrarComentario() {

        Comentario comentario = ComentarioHelper.criarComentario();

        ComentarioInsertDTO comentarioInsertDTO = new ComentarioInsertDTO(
                comentario.getReserva().getId(), comentario.getTexto()
        );
        when(comentar.execute(any(ComentarioInsertDTO.class))).thenReturn(comentario);
        var comentarioSalvo = comentar.execute(comentarioInsertDTO);

        assertThat(comentarioSalvo).isInstanceOf(Comentario.class).isNotNull();
        assertThat(comentarioSalvo).isEqualTo(comentario);

    }
}