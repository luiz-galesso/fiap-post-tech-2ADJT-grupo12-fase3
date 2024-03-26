package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarRestaurantePeloNome
{
    private final RestauranteGateway restauranteGateway;

    public BuscarRestaurantePeloNome(RestauranteGateway restauranteGateway)
    {
        this.restauranteGateway = restauranteGateway;
    }

    public List<Restaurante> execute(String nome)
    {
        return this.restauranteGateway.findByNomeContaining(nome);
    }

}
