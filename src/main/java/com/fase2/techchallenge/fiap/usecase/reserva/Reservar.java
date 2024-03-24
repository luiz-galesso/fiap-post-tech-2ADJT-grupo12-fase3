package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.cliente.ObterClientePeloId;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase2.techchallenge.fiap.usecase.mesa.ObterMesaPeloId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class Reservar {
    private final ReservaGateway reservaGateway;

    private final ObterMesaPeloId obterMesaPeloId;

    private final ObterClientePeloId obterClientePeloId;

    public Reserva execute(ReservaInsertDTO reservaInsertDTO) {

        Mesa mesa = obterMesaPeloId.execute(reservaInsertDTO.getIdRestaurante(), reservaInsertDTO.getIdMesa());

        if (!mesa.getSituacao().equals("ATIVO")) {
            throw new BussinessErrorException("Mesa não está disponível para ser reservada.");
        }

        Cliente cliente = obterClientePeloId.execute(reservaInsertDTO.getIdCliente());

        if (!cliente.getSituacao().equals("ATIVO")) {
            throw new BussinessErrorException("Cliente está inativo e não pode realizar uma reserva.");
        }

        List<Reserva> reservaList = reservaGateway.findByIdMesaAndSituacaoAndDataHoraInicioBetweenOrDataHoraFinalBetween(mesa.getId().getIdMesa(), "ATIVO",reservaInsertDTO.getDataHoraInicio(), reservaInsertDTO.getDataHoraInicio().plusHours(reservaInsertDTO.getQuantidadeHoras()));

        if(!reservaList.isEmpty())
            throw new BussinessErrorException("Já existe reserva para a mesa e horário escolhidos.");

        Reserva reserva =
                new Reserva(mesa,
                        cliente,
                        "ATIVO",
                        reservaInsertDTO.getDataHoraInicio(),
                        reservaInsertDTO.getDataHoraInicio().plusHours(reservaInsertDTO.getQuantidadeHoras())
                );

        return this.reservaGateway.create(reserva);
    }
}
