package com.fase2.techchallenge.fiap.infrastructure.comentario.repository;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
