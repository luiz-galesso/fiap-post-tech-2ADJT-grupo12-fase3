package com.fase3.techchallenge.fiap.infrastructure.avaliacao.repository;

import com.fase3.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
