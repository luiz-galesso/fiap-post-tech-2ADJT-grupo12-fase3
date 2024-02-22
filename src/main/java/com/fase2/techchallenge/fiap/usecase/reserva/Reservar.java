package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.mesa.ObterMesaPeloId;
import org.springframework.stereotype.Service;

@Service
public class Reservar {
    private final ReservaGateway reservaGateway;

    private final ObterMesaPeloId obterMesaPeloId;

    public Reservar(ReservaGateway reservaGateway, ObterMesaPeloId obterMesaPeloId) {
        this.reservaGateway = reservaGateway;
        this.obterMesaPeloId = obterMesaPeloId;
    }

    public Reserva execute(ReservaInsertDTO reservaInsertDTO) {

        Reserva reserva =
                new Reserva(obterMesaPeloId.execute(reservaInsertDTO.getIdRestaurante(), reservaInsertDTO.getIdMesa()),
                        "ATIVO",
                        reservaInsertDTO.getDataHoraInicio(),
                        reservaInsertDTO.getDataHoraFinal()
                );

        return this.reservaGateway.create(reserva);
    }
}
