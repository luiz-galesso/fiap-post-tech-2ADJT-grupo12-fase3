package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase2.techchallenge.fiap.utils.ClienteHelper;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CadastrarClienteTest {

    @Mock
    ClienteGateway clienteGateway;
    CadastrarCliente cadastrarCliente;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cadastrarCliente = new CadastrarCliente(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCadastrarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        when(clienteGateway.create(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        var clienteInserido = cadastrarCliente.execute(clienteInsertDTO);

        assertThat(clienteInserido).isInstanceOf(Cliente.class).isNotNull();
        assertThat(clienteInserido.getEmail()).isNotNull();
        assertThat(clienteInserido.getNome()).isEqualTo(cliente.getNome());
        assertThat(clienteInserido.getSituacao()).isEqualTo(cliente.getSituacao());
        assertThat(clienteInserido.getDataNascimento()).isEqualTo(cliente.getDataNascimento());
        assertThat(clienteInserido.getEndereco()).isEqualTo(cliente.getEndereco());
        verify(clienteGateway, times(1)).create(any(Cliente.class));
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarCliente_JaCadastrado() {
        var cliente = ClienteHelper.gerarCliente("lucas.pereira@example.com", "Lucas Pereira", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        when(clienteGateway.findById(cliente.getEmail())).thenReturn(Optional.of(cliente));

        assertThatThrownBy(() -> cadastrarCliente.execute(clienteInsertDTO)).isInstanceOf(BussinessErrorException.class).hasMessage("Já existe um cliente cadastrado com o email informado.");
        verify(clienteGateway, times(1)).findById(cliente.getEmail());
    }

}
