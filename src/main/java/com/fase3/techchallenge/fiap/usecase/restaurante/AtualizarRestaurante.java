package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteUpdateDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AtualizarRestaurante {
    private final RestauranteGateway restauranteGateway;

    public AtualizarRestaurante(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante execute(Long id, RestauranteUpdateDTO restauranteUpdateDTO) {
        Optional<Restaurante> optionalRestaurante = this.restauranteGateway.findById(id);

        if (optionalRestaurante.isEmpty()) return null;

        Restaurante restauranteAtualizado =
                new Restaurante(restauranteUpdateDTO.getNome(),
                        restauranteUpdateDTO.getCnpj(),
                        restauranteUpdateDTO.getEndereco(),
                        restauranteUpdateDTO.getTipoCulinaria(),
                        restauranteUpdateDTO.getCapacidade(),
                        restauranteUpdateDTO.getSituacao(),
                        restauranteUpdateDTO.getHorarioFuncionamento(),
                        LocalDateTime.now()
                );

        Restaurante restaurante = optionalRestaurante.get();

        // Obtendo todos os campos declarados da classe Restaurante
        Field[] campos = Restaurante.class.getDeclaredFields();

        //percorre os campos declarados
        for (Field campo : campos) {
            try {
                campo.setAccessible(true);
                Object valorAtualizado = campo.get(restauranteAtualizado);
                if (valorAtualizado != null) {
                    campo.set(restaurante, valorAtualizado);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return restauranteGateway.update(restaurante);

    }
}
