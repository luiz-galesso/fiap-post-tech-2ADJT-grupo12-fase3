package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AtualizarRestaurante
{
    private final RestauranteGateway restauranteGateway;

    public AtualizarRestaurante(RestauranteGateway restauranteGateway)
    {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante execute(Long id, RestauranteInsertDTO restauranteDTO)
    {
        Optional<Restaurante> optionalRestaurante = this.restauranteGateway.findById(id);

        if(optionalRestaurante.isEmpty()) return null;

         Restaurante restauranteAtualizado =
                new Restaurante(restauranteDTO.getNome(),
                        restauranteDTO.getCnpj(),
                        restauranteDTO.getEndereco(),
                        restauranteDTO.getTipoCulinaria(),
                        restauranteDTO.getCapacidade(),
                        restauranteDTO.getSituacao(),
                        restauranteDTO.getHorarioFuncionamento(),
                        LocalDateTime.now()
                );

         Restaurante restaurante = optionalRestaurante.get();

        // Obtendo todos os campos declarados da classe Restaurante
        Field[] campos = Restaurante.class.getDeclaredFields();

        //percorre os campos declarados
        for(Field campo : campos)
        {
            try
            {
                campo.setAccessible(true);
                Object valorAtualizado = campo.get(restauranteAtualizado);
                if(valorAtualizado != null)
                {
                    campo.set(restaurante, valorAtualizado);
                }
            } catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
        }

        return restauranteGateway.update(restaurante);

    }
}
