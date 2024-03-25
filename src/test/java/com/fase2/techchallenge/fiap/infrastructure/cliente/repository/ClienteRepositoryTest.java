package com.fase2.techchallenge.fiap.infrastructure.cliente.repository;


import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteRepositoryTest {
    @Mock
    ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void devePermitirRegistrarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        var clienteGerado = clienteRepository.save(cliente);

        assertThat(clienteGerado).isNotNull().isEqualTo(cliente);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void devePermitirBuscarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        when(clienteRepository.findById(any(String.class))).thenReturn(Optional.of(cliente));

        var clienteOptional = clienteRepository.findById(cliente.getEmail());

        assertThat(clienteOptional).isPresent().containsSame(cliente);
        clienteOptional.ifPresent(clienteRecebido -> {
            assertThat(clienteRecebido.getEmail()).isEqualTo(cliente.getEmail());
            assertThat(clienteRecebido.getNome()).isEqualTo(cliente.getNome());
            assertThat(clienteRecebido.getSituacao()).isEqualTo(cliente.getSituacao());
            assertThat(clienteRecebido.getDataNascimento()).isEqualTo(cliente.getDataNascimento());
        });
        verify(clienteRepository, times(1)).findById(any(String.class));
    }

    @Test
    void devePermitirRemoverCliente() {
        var id = "luiz.rocha@example.com";
        doNothing().when(clienteRepository).deleteById(any(String.class));

        clienteRepository.deleteById(id);

        verify(clienteRepository, times(1)).deleteById(any(String.class));
    }

    @Test
    void devePermitirListarClientes() {
        var cliente1 = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        var cliente2 = ClienteHelper.gerarCliente("jarla-santos@email.com", "Jarlan Santos", "INATIVO", LocalDate.of(1992, 01, 23));
        var listaClientes = Arrays.asList(cliente1, cliente2);
        when(clienteRepository.findAll()).thenReturn(listaClientes);

        var listaClientesEncontrados = clienteRepository.findAll();

        assertThat(listaClientesEncontrados).hasSize(2).containsExactlyInAnyOrder(cliente1, cliente2);
        verify(clienteRepository, times(1)).findAll();
    }

}
