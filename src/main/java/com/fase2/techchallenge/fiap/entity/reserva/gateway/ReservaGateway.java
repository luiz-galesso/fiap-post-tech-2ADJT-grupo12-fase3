package com.fase2.techchallenge.fiap.entity.reserva.gateway;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.repository.ReservaRepository;
import org.springframework.stereotype.Component;

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

}