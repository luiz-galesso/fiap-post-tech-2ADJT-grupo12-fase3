package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
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
public class BuscarRestaurantePeloTipoIT {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    BuscarRestaurantePeloTipo buscarRestaurantePeloTipo;

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantesPeloTipo() {
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var resultado = buscarRestaurantePeloTipo.execute("steakhouse");

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }

        @Test
        void deveBuscarRestaurantesPeloTipoSemResultados() {
            var resultado = buscarRestaurantePeloTipo.execute("<tipoInvalido>");
            assertThat(resultado).isEmpty();
        }

    }

}
