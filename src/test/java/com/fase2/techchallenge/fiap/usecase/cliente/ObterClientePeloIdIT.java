package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ObterClientePeloIdIT {

    @Autowired
    ObterClientePeloId obterClientePeloId;

    @Test
    void devePermitirBuscarCliente() {

        var id = "eduardo.melo@example.com";
        var cliente = obterClientePeloId.execute(id);

        assertThat(cliente).isInstanceOf(Cliente.class);
        assertThat(cliente).isNotNull();
        assertThat(cliente.getEmail()).isNotNull().isEqualTo(id);
    }

    @Test
    void deveGerarExcecao_QuandoBuscarCliente_NaoLocalizado() {
        var id = "joao-wick@email.com";

        assertThatThrownBy(() -> obterClientePeloId.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não localizado");
    }

}
