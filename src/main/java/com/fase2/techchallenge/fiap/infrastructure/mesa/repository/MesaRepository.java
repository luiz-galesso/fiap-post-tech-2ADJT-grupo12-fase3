package com.fase2.techchallenge.fiap.infrastructure.mesa.repository;

import com.fase2.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase2.techchallenge.fiap.entity.mesa.model.MesaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, MesaId> {
}
