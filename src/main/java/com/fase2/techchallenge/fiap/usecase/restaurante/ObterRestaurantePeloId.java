package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterRestaurantePeloId {
    private final RestauranteGateway restauranteGateway;

    public ObterRestaurantePeloId(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante execute(Long id) {
        return this.restauranteGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurante não localizado"));
    }


}