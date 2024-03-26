package com.fase3.techchallenge.fiap.infrastructure.mesa.repository;

import com.fase3.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase3.techchallenge.fiap.entity.mesa.model.MesaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, MesaId> {
}
