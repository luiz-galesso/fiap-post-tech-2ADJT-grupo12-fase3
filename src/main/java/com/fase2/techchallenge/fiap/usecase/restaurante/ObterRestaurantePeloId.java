package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.exception.RestauranteNotFoundException;
import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.stereotype.Service;

@Service
public class ObterRestaurantePeloId {
    private final RestauranteGateway restauranteGateway;

    public ObterRestaurantePeloId(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante execute(Long id) {
        return this.restauranteGateway.findById(id).orElseThrow();
    }


}