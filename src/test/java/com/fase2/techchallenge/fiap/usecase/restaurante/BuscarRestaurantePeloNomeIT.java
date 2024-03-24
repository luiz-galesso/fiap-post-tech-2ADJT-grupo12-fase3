package com.fase2.techchallenge.fiap.usecase.restaurante;

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
public class BuscarRestaurantePeloNomeIT {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    BuscarRestaurantePeloNome buscarRestaurantePeloNome;

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantesPeloNome() {
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var resultado = buscarRestaurantePeloNome.execute("Grill");

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }

        @Test
        void deveBuscarRestaurantesPeloNomeSemResultados() {
            var resultado = buscarRestaurantePeloNome.execute("<nomeInvalido>");
            assertThat(resultado).isEmpty();
        }

    }
}
