package com.fase3.techchallenge.fiap.entity.restaurante.gateway;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RestauranteGateway {

    private final RestauranteRepository restauranteRepository;

    public RestauranteGateway(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }
    public Restaurante create(Restaurante restaurante){
        return this.restauranteRepository.save(restaurante);
    }

    public Restaurante update(Restaurante restaurante){
        return this.restauranteRepository.save(restaurante);
    }

    public Optional<Restaurante> findById(Long id){
        return this.restauranteRepository.findById(id);
    }

    public List<Restaurante> findByNomeContaining(String nome){
        return this.restauranteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Restaurante> findByTipoCulinariaContaining(String tipCulinaria){
        return this.restauranteRepository.findByTipoCulinariaContainingIgnoreCase(tipCulinaria);
    }

    public List<Restaurante> findByEndereco(Endereco endereco)
    {
        Restaurante restaurante = new Restaurante(endereco);

        return this.restauranteRepository.findAll(Example.of(restaurante));
    }

}