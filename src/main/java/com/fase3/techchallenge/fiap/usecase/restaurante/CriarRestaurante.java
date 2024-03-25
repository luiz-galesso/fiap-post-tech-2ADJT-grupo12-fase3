package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CriarRestaurante {
    private final RestauranteGateway restauranteGateway;

    public CriarRestaurante(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante execute(RestauranteInsertDTO restauranteDTO) {

        Restaurante restaurante =
                new Restaurante(restauranteDTO.getNome(),
                        restauranteDTO.getCnpj(),
                        restauranteDTO.getEndereco(),
                        restauranteDTO.getTipoCulinaria(),
                        restauranteDTO.getCapacidade(),
                        "ATIVO",
                        restauranteDTO.getHorarioFuncionamento(),
                        LocalDateTime.now()
                );

        return this.restauranteGateway.create(restaurante);
    }


}