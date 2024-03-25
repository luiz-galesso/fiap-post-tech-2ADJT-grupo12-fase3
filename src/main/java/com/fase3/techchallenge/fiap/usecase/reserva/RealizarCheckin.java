package com.fase3.techchallenge.fiap.usecase.reserva;

import com.fase3.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RealizarCheckin {

    private final ReservaGateway reservaGateway;

    private final ObterReservaPeloId obterReservaPeloId;

    public Reserva execute(Long idReserva, String idCliente) {

        LocalDateTime dataHoraCheckin = LocalDateTime.now();

        Reserva reserva = obterReservaPeloId.execute(idReserva);

        if (!reserva.getCliente().getEmail().equals(idCliente)) {
            throw new BussinessErrorException("Reserva não pertence ao cliente informado.");
        }

        if (!reserva.getSituacao().equals("ATIVO")) {
            throw new BussinessErrorException("Não foi possível realizar o checkin. Reserva em situação inválida.");
        }
        if (dataHoraCheckin.isBefore(reserva.getDataHoraInicio())) {
            throw new BussinessErrorException("Aguarde o horário de ínicio da reserva para realizar o checkin.");
        }

        if (dataHoraCheckin.isAfter(reserva.getDataHoraFinal())) {
            throw new BussinessErrorException("Não é mais possível realizar o checkin, o horário final da reserva foi ultrapassado.");
        }

        reserva.setSituacao("CHECKIN");
        reserva.setDataHoraCheckin(dataHoraCheckin);

        return this.reservaGateway.update(reserva);
    }
}
