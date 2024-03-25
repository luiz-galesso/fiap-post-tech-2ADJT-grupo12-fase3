package com.fase3.techchallenge.fiap.usecase.cliente;

import com.fase3.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
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

public class AtualizarClienteTest {

    @Mock
    ClienteGateway clienteGateway;
    AtualizarCliente atualizarCliente;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        atualizarCliente = new AtualizarCliente(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAlterarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));

        when(clienteGateway.findById(cliente.getEmail())).thenReturn(Optional.of(cliente));
        when(clienteGateway.update(any(Cliente.class))).thenReturn(cliente);

        ClienteUpdateDTO ClienteUpdateDTO = new ClienteUpdateDTO("João Wick Silva"
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , Endereco.builder()
                .logradouro("Rua Jesuino Cardoso")
                .numero("12")
                .bairro("Jardins")
                .complemento("01 B")
                .cep(02456314L)
                .cidade("São Paulo")
                .estado("SP")
                .build());

        var clienteAlterado = atualizarCliente.execute(cliente.getEmail(), ClienteUpdateDTO);

        assertThat(clienteAlterado).isInstanceOf(Cliente.class).isNotNull();
        assertThat(clienteAlterado).usingRecursiveComparison().ignoringFields("email","dataRegistro").isEqualTo(cliente);
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, times(1)).update(any(Cliente.class));
    }

    @Test
    void deveGerarExcecao_QuandoAlterarCliente_IdNaoExiste() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteUpdateDTO ClienteUpdateDTO = new ClienteUpdateDTO(cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        when(clienteGateway.findById(cliente.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarCliente.execute(cliente.getEmail(), ClienteUpdateDTO))
                .isInstanceOf(BussinessErrorException.class).hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, never()).update(any(Cliente.class));
    }

}
