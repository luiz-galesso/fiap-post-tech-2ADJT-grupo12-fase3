package com.fase3.techchallenge.fiap.usecase.avaliacao;

import com.fase3.techchallenge.fiap.entity.avaliacao.gateway.AvaliacaoGateway;
import org.springframework.stereotype.Service;

@Service
public class ApagarAvaliacao {
    private final AvaliacaoGateway avaliacaoGateway;

    public ApagarAvaliacao(AvaliacaoGateway avaliacaoGateway) {
        this.avaliacaoGateway = avaliacaoGateway;
    }

    public void execute(Long id) {
        this.avaliacaoGateway.deleteById(id);
    }
}
