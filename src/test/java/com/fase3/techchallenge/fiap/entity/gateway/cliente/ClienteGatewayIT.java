package com.fase3.techchallenge.fiap.entity.gateway.cliente;

import com.fase3.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ClienteGatewayIT {

    @Autowired
    ClienteGateway clienteGateway;

    @Test
    void devePermitirRegistrarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));

        var clienteCriado = clienteGateway.create(cliente);

        assertThat(clienteCriado).isInstanceOf(Cliente.class);
        assertThat(clienteCriado.getEmail()).isNotNull();
    }

    @Test
    void devePermitirAtualizarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        var clienteAtualizado = clienteGateway.update(cliente);

        assertThat(clienteAtualizado).isInstanceOf(Cliente.class);
        assertThat(clienteAtualizado).isNotNull();
        assertThat(clienteAtualizado.getEmail()).isEqualTo(cliente.getEmail());
    }

    @Test
    void devePermitirBuscarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));

        var clienteRetornado = clienteGateway.findById(cliente.getEmail());

        assertThat(clienteRetornado).isInstanceOf(Optional.of(cliente).getClass());
    }

}
