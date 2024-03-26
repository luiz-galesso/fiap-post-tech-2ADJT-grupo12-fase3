package com.fase3.techchallenge.fiap.usecase.cliente;

import com.fase3.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoverClientePeloId {
    private final ClienteGateway clienteGateway;

    public RemoverClientePeloId(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public boolean execute(String id) {
        Optional<Cliente> clienteOptional = clienteGateway.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o cliente cadastrado com o email informado.");
        }

        clienteGateway.remove(id);
        return true;
    }

}