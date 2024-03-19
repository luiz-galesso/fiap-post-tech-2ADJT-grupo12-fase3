package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CancelarReserva {

    private final ReservaGateway reservaGateway;

    private final ObterReservaPeloId obterReservaPeloId;

    public Reserva execute(Long idReserva, String idCliente) {

        Reserva reserva = obterReservaPeloId.execute(idReserva);

        if (!reserva.getCliente().getEmail().equals(idCliente)) {
            throw new BussinessErrorException("Reserva não pertence ao cliente informado.");
        }

        if (!reserva.getSituacao().equals("ATIVO")) {
            throw new BussinessErrorException("Não foi possível cancelar a reserva. Reserva em situação inválida.");
        }

        reserva.setSituacao("CANCELADA");

        return this.reservaGateway.update(reserva);
    }
}
