package com.fase2.techchallenge.fiap.entity.avaliacao.gateway;

import com.fase2.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase2.techchallenge.fiap.infrastructure.avaliacao.repository.AvaliacaoRepository;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AvaliacaoGateway {

    private AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoGateway(RestauranteRepository restauranteRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }
    public Avaliacao create(Avaliacao avaliacao){
        return this.avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao update(Avaliacao avaliacao){
        return this.avaliacaoRepository.save(avaliacao);
    }

    public Optional<Avaliacao> findById(Long id){
        return this.avaliacaoRepository.findById(id);
    }

    public void deleteById(Long id) {this.avaliacaoRepository.deleteById(id);}

}