package com.fase3.techchallenge.fiap.usecase.cliente;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase3.techchallenge.fiap.infrastructure.cliente.repository.ClienteRepository;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
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
public class AtualizarClienteIT {

    @Autowired
    AtualizarCliente atualizarCliente;

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    void devePermitirAlterarCliente() {
        var cliente = ClienteHelper.gerarCliente("eduardo.melo@example.com", "Eduardo Melo", "ATIVO", LocalDate.of(1990, 05, 19));
        var clienteAtual = clienteRepository.findById(cliente.getEmail());
        cliente.setNome("Eduardo Melo Santos");
        cliente.setEndereco(Endereco.builder()
                .logradouro("Rua Jesuino Cardoso")
                .numero("12")
                .bairro("Jardins")
                .complemento("01 B")
                .cep(02456314L)
                .cidade("São Paulo")
                .estado("SP")
                .build());
        ClienteUpdateDTO clienteUpdateDTO = new ClienteUpdateDTO(cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        var clienteAlterado = atualizarCliente.execute(cliente.getEmail(), clienteUpdateDTO);

        assertThat(clienteAlterado).isNotNull();
        assertThat(clienteAlterado).usingRecursiveComparison().ignoringFields("email","dataRegistro").isEqualTo(cliente);

    }

    @Test
    void deveGerarExcecao_QuandoAlterarCliente_ClienteNaoCadastrado() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        ClienteUpdateDTO clienteUpdateDTO = new ClienteUpdateDTO(cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        assertThatThrownBy(() -> atualizarCliente.execute(cliente.getEmail(), clienteUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");

    }

}
