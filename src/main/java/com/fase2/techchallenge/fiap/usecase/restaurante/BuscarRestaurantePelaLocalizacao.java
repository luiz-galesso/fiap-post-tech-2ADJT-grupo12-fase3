package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarRestaurantePelaLocalizacao
{
    private final RestauranteGateway restauranteGateway;

    public BuscarRestaurantePelaLocalizacao(RestauranteGateway restauranteGateway)
    {
        this.restauranteGateway = restauranteGateway;
    }

    public List<Restaurante> execute(Endereco endereco)
    {
        return this.restauranteGateway.findByEndereco(endereco);
    }
}
