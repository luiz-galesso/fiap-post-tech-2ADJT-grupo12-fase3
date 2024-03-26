package com.fase3.techchallenge.fiap.usecase.cliente;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class CadastrarClienteIT {

    @Autowired
    CadastrarCliente cadastrarCliente;

    @Test
    void devePermitirCadastrarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        var clienteInserido = cadastrarCliente.execute(clienteInsertDTO);

        Assertions.assertThat(clienteInserido).isInstanceOf(Cliente.class);
        Assertions.assertThat(clienteInserido).isNotNull();
        Assertions.assertThat(clienteInserido.getEmail()).isEqualTo(cliente.getEmail());
        Assertions.assertThat(clienteInserido.getNome()).isEqualTo(cliente.getNome());
        Assertions.assertThat(clienteInserido.getDataNascimento()).isEqualTo(cliente.getDataNascimento());
        Assertions.assertThat(clienteInserido.getSituacao()).isEqualTo(cliente.getSituacao());
        Assertions.assertThat(clienteInserido.getEndereco()).isEqualTo(cliente.getEndereco());
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarCliente_JaCadastrado() {
        var cliente = ClienteHelper.gerarCliente("lucas.pereira@example.com", "Lucas Pereira", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        assertThatThrownBy(() -> cadastrarCliente.execute(clienteInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Já existe um cliente cadastrado com o email informado.");
    }
}
