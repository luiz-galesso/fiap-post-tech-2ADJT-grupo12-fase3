package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class RemoverClientePeloIdIT {

    @Autowired
    RemoverClientePeloId removerClientePeloId;

    @Test
    void devePermitirRemoverCliente() {
        var id = "eduardo.melo@example.com";
        var retorno = removerClientePeloId.execute(id);

        assertThat(retorno).isTrue();
    }

    @Test
    void deveGerarExcecao_QuandoRemoverCliente_IdNaoExiste() {
        var id = "francisco-filho@email.com";

        assertThatThrownBy(() -> removerClientePeloId.execute(id)).isInstanceOf(BussinessErrorException.class).hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
    }

}
