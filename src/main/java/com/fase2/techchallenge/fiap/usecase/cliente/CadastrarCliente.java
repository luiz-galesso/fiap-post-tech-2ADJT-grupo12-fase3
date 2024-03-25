package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarCliente {

    private final ClienteGateway clienteGateway;

    public Cliente execute(ClienteInsertDTO clienteDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(clienteDTO.getEmail());

        if (clienteOptional.isPresent()) {
            throw new BussinessErrorException("Já existe um cliente cadastrado com o email informado.");
        }

        Cliente cliente =
                new Cliente(clienteDTO.getEmail(),
                        clienteDTO.getNome(),
                        "ATIVO",
                        LocalDateTime.now(),
                        clienteDTO.getDataNascimento(),
                        clienteDTO.getEndereco()
                );
        return this.clienteGateway.create(cliente);
    }
}
