package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
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
import static org.mockito.Mockito.when;

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

        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , "João Wick Silva"
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

        var clienteAlterado = atualizarCliente.execute(cliente.getEmail(), clienteInsertDTO);

        assertThat(clienteAlterado).isInstanceOf(Cliente.class).isNotNull();
        assertThat(clienteAlterado).isEqualTo(cliente);
    }

    @Test
    void deveGerarExcecao_QuandoAlterarCliente_IdNaoExiste() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        when(clienteGateway.findById(cliente.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarCliente.execute(cliente.getEmail(), clienteInsertDTO))
                .isInstanceOf(BussinessErrorException.class).hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
    }

}
