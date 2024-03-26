package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
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
public class BuscarRestaurantePelaLocalizacaoIT {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    BuscarRestaurantePelaLocalizacao buscarRestaurantePelaLocalizacao;

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantesPelaLocalizacao() {
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var enderecoBusca = new Endereco();
            enderecoBusca.setCidade("Umuarama");
            enderecoBusca.setEstado("PR");
            var resultado = buscarRestaurantePelaLocalizacao.execute(enderecoBusca);

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }

        @Test
        void deveBuscarRestaurantesPelaLocalizacaoSemResultados() {
            var enderecoBusca = new Endereco();
            enderecoBusca.setCidade("<cidadeInvalida>");
            enderecoBusca.setEstado("<estadoInvalido>");
            var resultado = buscarRestaurantePelaLocalizacao.execute(enderecoBusca);
            assertThat(resultado).isEmpty();
        }

    }
}
