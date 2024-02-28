package com.fase2.techchallenge.fiap.entity.comentario.gateway;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.infrastructure.comentario.repository.ComentarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ComentarioGateway {

    private ComentarioRepository comentarioRepository;

    public ComentarioGateway(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }
    public Comentario create(Comentario comentario){
        return this.comentarioRepository.save(comentario);
    }

    public Comentario update(Comentario comentario){
        return this.comentarioRepository.save(comentario);
    }

    public Optional<Comentario> findById(Long id){
        return this.comentarioRepository.findById(id);
    }

    public void deleteById(Long id){
        this.comentarioRepository.deleteById(id);
    }

}