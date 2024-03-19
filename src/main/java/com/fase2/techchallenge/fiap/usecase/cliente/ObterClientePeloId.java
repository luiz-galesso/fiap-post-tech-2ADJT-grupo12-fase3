package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterClientePeloId {
    private final ClienteGateway clienteGateway;

    public ObterClientePeloId(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(String id) {
        return this.clienteGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não localizado"));
    }


}