package com.fase3.techchallenge.fiap.usecase.comentario;


import com.fase3.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.infrastructure.comentario.utils.ComentarioHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class ApagarComentarioTest {

    @Mock
    ApagarComentario apagarComentario;

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
    void devePermitirApagarComentario() {
        Comentario comentario = ComentarioHelper.criarComentario();

        //Act
        apagarComentario.execute(comentario.getId());

        var comentarioOptional = comentarioGateway.findById(comentario.getId());

        // Assert
        assertThat(comentarioOptional)
                .isEmpty();


    }

}
