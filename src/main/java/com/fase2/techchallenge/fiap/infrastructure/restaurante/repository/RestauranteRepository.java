package com.fase2.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>
{
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    List<Restaurante> findByTipoCulinariaContainingIgnoreCase(String tipoCulinaria);
}
