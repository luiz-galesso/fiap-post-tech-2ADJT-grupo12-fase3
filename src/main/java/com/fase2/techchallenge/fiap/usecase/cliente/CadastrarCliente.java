package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CadastrarCliente {

    private final ClienteGateway clienteGateway;

    public CadastrarCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(ClienteInsertDTO clienteDTO) {

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
