package com.fase3.techchallenge.fiap.usecase.cliente;

import com.fase3.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarCliente {

    private final ClienteGateway clienteGateway;

    public Cliente execute(String email, ClienteUpdateDTO clienteUpdateDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(email);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o cliente cadastrado com o email informado.");
        }

        Cliente cliente = new Cliente(email,
                clienteUpdateDTO.getNome(),
                clienteUpdateDTO.getSituacao(),
                clienteOptional.get().getDataRegistro(),
                clienteUpdateDTO.getDataNascimento(),
                clienteUpdateDTO.getEndereco()
        );
        return this.clienteGateway.update(cliente);
    }
}
