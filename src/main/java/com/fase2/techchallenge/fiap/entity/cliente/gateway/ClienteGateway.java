package com.fase2.techchallenge.fiap.entity.cliente.gateway;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.infrastructure.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteGateway {

    private ClienteRepository clienteRepository;

    public ClienteGateway(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente create(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(String email) {
        return this.clienteRepository.findById(email);
    }

    public void remove(String email) {
        clienteRepository.deleteById(email);
    }

}