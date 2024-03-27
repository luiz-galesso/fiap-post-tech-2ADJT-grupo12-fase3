package com.fase3.techchallenge.fiap.entity.cliente.gateway;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.infrastructure.cliente.repository.ClienteRepository;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteGatewayTest {

    ClienteGateway clienteGateway;

    @Mock
    ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteGateway = new ClienteGateway(clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        var clienteCriado = clienteGateway.create(cliente);

        assertThat(clienteCriado).isInstanceOf(Cliente.class);
        assertThat(clienteCriado.getEmail()).isNotNull();
    }

    @Test
    void devePermitirAtualizarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        var clienteAtualizado = clienteGateway.update(cliente);

        assertThat(clienteAtualizado).isInstanceOf(Cliente.class);
        assertThat(clienteAtualizado).isNotNull();
        assertThat(clienteAtualizado.getEmail()).isEqualTo(cliente.getEmail());
    }

    @Test
    void devePermitirBuscarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));

        when(clienteRepository.findById(any(String.class))).thenReturn(Optional.of(cliente));
        var clienteRetornado = clienteGateway.findById(cliente.getEmail());

        assertThat(clienteRetornado).isInstanceOf(Optional.of(cliente).getClass());
    }

}
