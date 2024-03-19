package com.fase2.techchallenge.fiap.usecase.reserva;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.usecase.cliente.ObterClientePeloId;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BuscarReservasAtivasPorCliente {

    private final ObterClientePeloId obterClientePeloId;

    private final ReservaGateway reservaGateway;

    public Page<Reserva> execute (String idCliente, Pageable pageable){

        Cliente cliente = obterClientePeloId.execute(idCliente);

        return reservaGateway.findBySituacaoAndCliente("ATIVO", cliente, pageable);

    }
}
