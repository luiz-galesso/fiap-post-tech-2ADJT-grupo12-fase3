package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteUpdateDTO;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AtualizarRestauranteIT {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    AtualizarRestaurante atualizarRestaurante;

    @Nested
    class AtualizarRestauranteTeste {
        @Test
        void deveAtualizarRestaurante() {
            var restauranteDesatualizado = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restauranteDesatualizadoSituacao = restauranteDesatualizado.getSituacao();
            var restauranteUpdateDTO = new RestauranteUpdateDTO(restauranteDesatualizado.getNome()
                    , restauranteDesatualizado.getCnpj()
                    , restauranteDesatualizado.getEndereco()
                    , restauranteDesatualizado.getTipoCulinaria()
                    , restauranteDesatualizado.getCapacidade()
                    , "INATIVO"
                    , restauranteDesatualizado.getHorarioFuncionamento());

            var resultado = atualizarRestaurante.execute(restauranteDesatualizado.getId(),restauranteUpdateDTO);

            assertThat(resultado)
                    .isNotNull()
                    .isInstanceOf(Restaurante.class)
                    .usingRecursiveComparison()
                    .ignoringFields("situacao")
                    .isEqualTo(restauranteDesatualizado);

            assertThat(resultado.getSituacao())
                    .isNotEqualTo(restauranteDesatualizadoSituacao)
                    .isNotNull();
        }

    }
}
