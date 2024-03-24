package com.fase2.techchallenge.fiap.usecase.comentario;

import com.fase2.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service public class EditarComentario {

    private final ComentarioGateway comentarioGateway;

    public EditarComentario(ComentarioGateway comentarioGateway) {
        this.comentarioGateway = comentarioGateway;
    }

    public Comentario execute(Long id, String texto) {

        Comentario comentario = comentarioGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado."));
        comentario.setTexto(texto);

        return this.comentarioGateway.update(comentario);
    }
}
