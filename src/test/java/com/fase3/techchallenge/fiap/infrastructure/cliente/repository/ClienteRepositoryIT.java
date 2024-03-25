package com.fase3.techchallenge.fiap.infrastructure.cliente.repository;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ClienteRepositoryIT {

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    void devePermitirCriarTabela() {
        var totalDeRegistros = clienteRepository.count();
        assertThat(totalDeRegistros).isNotNegative();
    }

    @Test
    void devePermitirRegistrarCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
        var clienteGerado = clienteRepository.save(cliente);

        assertThat(clienteGerado).isInstanceOf(Cliente.class).isNotNull();
        assertThat(clienteGerado).isEqualTo(cliente);
    }

    @Test
    void devePermitirBuscarCliente() {
        var id = "luiz.rocha@example.com";
        var clienteOptional = clienteRepository.findById(id);

        assertThat(clienteOptional).isPresent();
        clienteOptional.ifPresent(cliente -> {
            assertThat(cliente.getEmail()).isEqualTo(id);
        });
    }

    @Test
    void devePermitirRemoverCliente() {
        var id = "luiz.rocha@example.com";
        clienteRepository.deleteById(id);
        var clienteOptional = clienteRepository.findById(id);

        assertThat(clienteOptional).isEmpty();
    }

    @Test
    void devePermitirListarClientes() {
        var resultadosObtidos = clienteRepository.findAll();

        assertThat(resultadosObtidos).hasSizeGreaterThan(0);
    }

}
