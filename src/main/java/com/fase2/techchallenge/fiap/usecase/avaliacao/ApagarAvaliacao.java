package com.fase2.techchallenge.fiap.usecase.avaliacao;

import com.fase2.techchallenge.fiap.entity.avaliacao.gateway.AvaliacaoGateway;
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
