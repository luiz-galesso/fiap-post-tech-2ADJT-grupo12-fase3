package com.fase2.techchallenge.fiap.entity.reserva.gateway;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.reserva.repository.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReservaGateway {

    private ReservaRepository reservaRepository;

    public ReservaGateway(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
    public Reserva create(Reserva reserva){
        return this.reservaRepository.save(reserva);
    }

    public Reserva update(Reserva reserva){
        return this.reservaRepository.save(reserva);
    }

    public Optional<Reserva> findById(Long id){
        return this.reservaRepository.findById(id);
    }

    public List<Reserva> findByMesaIdRestauranteAndDataHoraInicioBetween(Restaurante restaurante, LocalDateTime dataInicio, LocalDateTime dataFinal){
        return this.reservaRepository.findByMesaIdRestauranteAndDataHoraInicioBetween(restaurante, dataInicio, dataFinal);
    }

    public Page<Reserva> findBySituacaoAndCliente(String situacao, Cliente cliente, Pageable pageable) {
        return this.reservaRepository.findBySituacaoAndCliente(situacao, cliente, pageable);
    }

    public List<Reserva>  findByIdMesaAndSituacaoAndDataHoraInicioBetweenOrDataHoraFinalBetween(Long idMesa, String situacao, LocalDateTime dataHoraInicioNovaReserva, LocalDateTime dataHoraFinalNovaReserva)  {
            return this.reservaRepository.reservaExists(idMesa, situacao, dataHoraInicioNovaReserva, dataHoraFinalNovaReserva);
    }
}