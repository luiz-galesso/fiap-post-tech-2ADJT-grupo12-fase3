package com.fase2.techchallenge.fiap.usecase.avaliacao;

import com.fase2.techchallenge.fiap.entity.avaliacao.gateway.AvaliacaoGateway;
import com.fase2.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import org.springframework.stereotype.Service;

@Service
public class EditarAvaliacao {
    private final AvaliacaoGateway avaliacaoGateway;

    public EditarAvaliacao(AvaliacaoGateway avaliacaoGateway) {
        this.avaliacaoGateway = avaliacaoGateway;
    }

    public Avaliacao execute(Long id, Integer valor) {

        Avaliacao avaliacao = avaliacaoGateway.findById(id).orElseThrow();
        /*TODO VALIDAR SE VALOR 0 A 5*/
        avaliacao.setValor(valor);

        return this.avaliacaoGateway.update(avaliacao);
    }
}
