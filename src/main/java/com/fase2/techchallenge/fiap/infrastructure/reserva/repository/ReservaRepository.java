package com.fase2.techchallenge.fiap.infrastructure.reserva.repository;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
