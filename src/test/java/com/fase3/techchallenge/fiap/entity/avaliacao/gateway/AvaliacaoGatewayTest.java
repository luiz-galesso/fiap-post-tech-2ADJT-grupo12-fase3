package com.fase3.techchallenge.fiap.entity.avaliacao.gateway;

import com.fase3.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase3.techchallenge.fiap.infrastructure.avaliacao.repository.AvaliacaoRepository;
import com.fase3.techchallenge.fiap.utils.AvaliacaoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AvaliacaoGatewayTest {
    AutoCloseable openMocks;
    private AvaliacaoGateway avaliacaoGateway;
    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        avaliacaoGateway = new AvaliacaoGateway(avaliacaoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarAvaliacao() {
        Avaliacao avaliacao = AvaliacaoHelper.gerarAvaliacao(null);

        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        var resultado = avaliacaoGateway.create(avaliacao);

        verify(avaliacaoRepository, times(1)).save(avaliacao);
        assertThat(resultado)
                .isInstanceOf(Avaliacao.class)
                .isNotNull();
        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(avaliacao);
    }

    @Test
    void devePermitirAlterarAvaliacao() {
        Avaliacao avaliacaoDesatualizada = AvaliacaoHelper.gerarAvaliacao(1L);
        var avaliacaoAtualizada = AvaliacaoHelper.gerarAvaliacao(1L);
        avaliacaoAtualizada.setValor(2);

        when(avaliacaoRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(avaliacaoDesatualizada));
        when(avaliacaoRepository.save(any(Avaliacao.class)))
                .thenAnswer(i -> i.getArgument(0));

        var resultado = avaliacaoGateway.update(avaliacaoAtualizada);

        assertThat(resultado)
                .isInstanceOf(Avaliacao.class)
                .isNotNull();

        assertNotEquals(resultado.getValor(), avaliacaoDesatualizada.getValor());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(avaliacaoAtualizada);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    void devePermitirBuscarAvaliacao() {
        Avaliacao avaliacao = AvaliacaoHelper.gerarAvaliacao(null);
        when(avaliacaoRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(avaliacao));

        var resultado = avaliacaoGateway.findById(any(Long.class));

        verify(avaliacaoRepository, times(1))
                .findById(any(Long.class));
        assertThat(resultado)
                .isPresent()
                .isInstanceOf(Optional.class)
                .isNotNull();

        assertEquals(avaliacao, resultado.get());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(avaliacao));
    }

    @Test
    void devePermitirDeletar() {
        Avaliacao avaliacao = AvaliacaoHelper.gerarAvaliacao(null);
        doNothing().when(avaliacaoRepository).deleteById(avaliacao.getId());

        avaliacaoGateway.deleteById(avaliacao.getId());
        var avaliacaoOptional = avaliacaoGateway.findById(avaliacao.getId());

        assertThat(avaliacaoOptional)
                .isEmpty();

        verify(avaliacaoRepository, times(1)).deleteById(avaliacao.getId());
    }
}
