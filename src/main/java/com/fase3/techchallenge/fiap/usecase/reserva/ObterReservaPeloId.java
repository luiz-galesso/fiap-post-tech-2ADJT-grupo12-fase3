package com.fase3.techchallenge.fiap.usecase.reserva;

import com.fase3.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ObterReservaPeloId {
    private final ReservaGateway reservaGateway;

    public ObterReservaPeloId(ReservaGateway reservaGateway) {
        this.reservaGateway = reservaGateway;
    }

    public Reserva execute(Long id) {
        return this.reservaGateway.findById(id).orElseThrow();
    }
}
