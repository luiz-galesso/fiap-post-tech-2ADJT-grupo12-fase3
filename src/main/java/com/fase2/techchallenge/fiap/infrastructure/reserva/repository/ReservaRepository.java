package com.fase2.techchallenge.fiap.infrastructure.reserva.repository;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByMesaIdRestauranteAndDataHoraInicioBetween(Restaurante restaurante, LocalDateTime dataInicio, LocalDateTime dataFinal);

    Page<Reserva> findBySituacaoAndCliente(String situacao, Cliente cliente, Pageable pageable);

}
