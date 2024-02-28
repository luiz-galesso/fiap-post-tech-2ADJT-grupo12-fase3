package com.fase2.techchallenge.fiap.usecase.comentario;

import com.fase2.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase2.techchallenge.fiap.entity.comentario.gateway.ComentarioGateway;
import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;

public class EditarComentario {

    private final ComentarioGateway comentarioGateway;

    public EditarComentario(ComentarioGateway comentarioGateway) {
        this.comentarioGateway = comentarioGateway;
    }

    public Comentario execute(Long id, String texto) {

        Comentario comentario = comentarioGateway.findById(id).orElseThrow();
        comentario.setTexto(texto);

        return this.comentarioGateway.update(comentario);
    }
}
