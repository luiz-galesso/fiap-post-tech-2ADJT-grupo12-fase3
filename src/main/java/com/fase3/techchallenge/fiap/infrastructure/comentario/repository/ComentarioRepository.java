package com.fase3.techchallenge.fiap.infrastructure.comentario.repository;

import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
