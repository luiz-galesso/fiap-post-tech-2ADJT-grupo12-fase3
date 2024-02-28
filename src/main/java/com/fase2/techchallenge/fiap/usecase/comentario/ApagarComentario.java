package com.fase2.techchallenge.fiap.usecase.comentario;

import com.fase2.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import org.springframework.stereotype.Service;

@Service
public class ApagarComentario {
    private final ComentarioGateway comentarioGateway;

    public ApagarComentario(ComentarioGateway comentarioGateway) {
        this.comentarioGateway = comentarioGateway;
    }

    public void execute(Long id) {
        this.comentarioGateway.deleteById(id);
    }
}
