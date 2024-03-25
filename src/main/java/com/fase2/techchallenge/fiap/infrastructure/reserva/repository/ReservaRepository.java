package com.fase2.techchallenge.fiap.infrastructure.reserva.repository;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByMesaIdRestauranteAndDataHoraInicioBetween(Restaurante restaurante, LocalDateTime dataInicio, LocalDateTime dataFinal);

    Page<Reserva> findBySituacaoAndCliente(String situacao, Cliente cliente, Pageable pageable);

    @Query("select r from Reserva r " +
            " where r.mesa.id.idMesa = :idMesa " +
            "   and r.situacao = :situacao " +
            "   and ((( r.dataHoraInicio >= :dataHoraInicio " +
            "       and r.dataHoraInicio < :dataHoraFinal) " +
            "  or (r.dataHoraInicio <= :dataHoraInicio " +
            "     and r.dataHoraInicio > :dataHoraFinal)) or " +
            "   (( r.dataHoraFinal > :dataHoraInicio " +
            "   and r.dataHoraFinal <= :dataHoraFinal) " +
            "  or (r.dataHoraFinal < :dataHoraInicio " +
            "  and r.dataHoraFinal >= :dataHoraFinal)))"
    )
    List<Reserva> reservaExists(@Param("idMesa") Long idMesa,
                                @Param("situacao") String situacao,
                                @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
                                @Param("dataHoraFinal") LocalDateTime dataHoraFinal);
}
