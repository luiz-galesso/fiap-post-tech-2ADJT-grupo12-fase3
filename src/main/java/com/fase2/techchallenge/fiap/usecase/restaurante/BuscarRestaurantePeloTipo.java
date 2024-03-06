package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarRestaurantePeloTipo
{
    private final RestauranteGateway restauranteGateway;

    public BuscarRestaurantePeloTipo(RestauranteGateway restauranteGateway)
    {
        this.restauranteGateway = restauranteGateway;
    }

    public List<Restaurante> execute(String tipoCulinaria)
    {
        return this.restauranteGateway.findByTipoCulinariaContaining(tipoCulinaria);
    }
}
