package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import com.fase3.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ObterRestaurantePeloIdIT {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    ObterRestaurantePeloId obterRestaurantePeloId;

    @Nested
    class ObterRestaurante {
        @Test
        void deveObterRestaurantePeloId() {
            var restaurante = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));

            var restauranteObtido = obterRestaurantePeloId.execute(restaurante.getId());

            assertThat(restauranteObtido)
                    .isNotNull()
                    .isInstanceOf(Restaurante.class)
                    .usingRecursiveComparison()
                    .isEqualTo(restaurante);
        }

        @Test
        void deveGerarErroQuandoNaoEncontrarRestaurantePorId(){
            assertThatThrownBy(() -> obterRestaurantePeloId.execute(-1L))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Restaurante não localizado");
        }

    }
}
