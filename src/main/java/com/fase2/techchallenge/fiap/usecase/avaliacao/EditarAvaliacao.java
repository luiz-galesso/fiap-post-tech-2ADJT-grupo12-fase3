package com.fase2.techchallenge.fiap.usecase.avaliacao;

import com.fase2.techchallenge.fiap.entity.avaliacao.gateway.AvaliacaoGateway;
import com.fase2.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EditarAvaliacao {
    private final AvaliacaoGateway avaliacaoGateway;

    public EditarAvaliacao(AvaliacaoGateway avaliacaoGateway) {
        this.avaliacaoGateway = avaliacaoGateway;
    }

    public Avaliacao execute(Long id, Integer valor) {

        Avaliacao avaliacao = avaliacaoGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada."));
        if (valor < 0 || valor > 5) {
            throw new BussinessErrorException("Valor inválido para avaliação.");
        }
        avaliacao.setValor(valor);

        return this.avaliacaoGateway.update(avaliacao);
    }
}
