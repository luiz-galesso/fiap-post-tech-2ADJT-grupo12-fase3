package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.cliente.ObterClientePeloId;
import com.fase2.techchallenge.fiap.usecase.mesa.ObterMesaPeloId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Reservar {
    private final ReservaGateway reservaGateway;

    private final ObterMesaPeloId obterMesaPeloId;

    private final ObterClientePeloId obterClientePeloId;

    public Reserva execute(ReservaInsertDTO reservaInsertDTO) {

        /*TODO VERIFICAR SE EXISTE RESERVA JA PRA AQUELE HORARIO E MESA */

        Reserva reserva =
                new Reserva(obterMesaPeloId.execute(reservaInsertDTO.getIdRestaurante(), reservaInsertDTO.getIdMesa()),
                        obterClientePeloId.execute(reservaInsertDTO.getIdCliente()),
                        "ATIVO",
                        reservaInsertDTO.getDataHoraInicio(),
                        reservaInsertDTO.getDataHoraFinal()
                );

        return this.reservaGateway.create(reserva);
    }
}
