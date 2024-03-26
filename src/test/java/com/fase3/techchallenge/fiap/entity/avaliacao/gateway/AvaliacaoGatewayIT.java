package com.fase3.techchallenge.fiap.entity.avaliacao.gateway;

import com.fase3.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase3.techchallenge.fiap.infrastructure.avaliacao.repository.AvaliacaoRepository;
import com.fase3.techchallenge.fiap.utils.AvaliacaoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AvaliacaoGatewayIT {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoGateway avaliacaoGateway;
    @Test
    void devePermitirCriarAvaliacao() {
        var avaliacao = AvaliacaoHelper.registrarAvaliacao(avaliacaoRepository,AvaliacaoHelper.gerarAvaliacao(null));

        var restauranteArmazenado = avaliacaoGateway.create(avaliacao);

        assertThat(restauranteArmazenado)
                .isNotNull()
                .isInstanceOf(Avaliacao.class);

        assertThat(restauranteArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(avaliacao);

        assertThat(restauranteArmazenado.getId())
                .isNotNull();

        assertThat(restauranteArmazenado.getDataRegistro())
                .isNotNull();
    }

    @Test
    void devePermitirAlterarAvaliacao() {

        var avaliacaoDesatualizada = AvaliacaoHelper.registrarAvaliacao(avaliacaoRepository, AvaliacaoHelper.gerarAvaliacao(null));
        var valoravaliacaoDesatualizada = avaliacaoDesatualizada.getValor();
        var avaliacaoAtualizada = avaliacaoDesatualizada.toBuilder().valor(2).build();

        var resultado = avaliacaoGateway.update(avaliacaoAtualizada);

        assertThat(resultado)
                .isInstanceOf(Avaliacao.class)
                .isNotNull();

        assertNotEquals(resultado.getValor(), valoravaliacaoDesatualizada);
        assertEquals(resultado.getId(), avaliacaoDesatualizada.getId());

        assertThat(resultado)
                .usingRecursiveComparison()
                .ignoringFields("valor")
                .isEqualTo(avaliacaoDesatualizada);
    }
    @Test
    void devePermitirBuscarAvaliacao() {
        var avaliacao = AvaliacaoHelper.registrarAvaliacao(avaliacaoRepository, AvaliacaoHelper.gerarAvaliacao(null));

        var avaliacaoOptional = avaliacaoGateway.findById(avaliacao.getId());

        assertThat(avaliacaoOptional)
                .isPresent()
                .containsSame(avaliacao);
        avaliacaoOptional.ifPresent(restauranteObtido -> {
            assertThat(restauranteObtido)
                    .usingRecursiveComparison()
                    .isEqualTo(avaliacao);
        });
    }

    @Test
    void devePermitirDeletar() {
        var avaliacao = AvaliacaoHelper.registrarAvaliacao(avaliacaoRepository, AvaliacaoHelper.gerarAvaliacao(null));

        avaliacaoGateway.deleteById(avaliacao.getId());

        var avaliacaoOptional = avaliacaoGateway.findById(avaliacao.getId());

        assertThat(avaliacaoOptional)
                .isEmpty();
    }
}
