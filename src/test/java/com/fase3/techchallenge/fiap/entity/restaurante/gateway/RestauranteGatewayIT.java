package com.fase3.techchallenge.fiap.entity.restaurante.gateway;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class RestauranteGatewayIT {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteGateway restauranteGateway;

    @Test
    void devePermitirCriarRestaurante() {
        var restaurante = RestauranteHelper.gerarRestaurante(null);

        var restauranteArmazenado = restauranteGateway.create(restaurante);

        assertThat(restauranteArmazenado)
                .isNotNull()
                .isInstanceOf(Restaurante.class);

        assertThat(restauranteArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(restaurante);

        assertThat(restauranteArmazenado.getId())
                .isNotNull();

        assertThat(restauranteArmazenado.getDataRegistro())
                .isNotNull();

    }

    @Test
    void devePermitirAlterarRestaurante() {

        var restauranteDesatualizado = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
        var nomeAntigo = restauranteDesatualizado.getNome();
        var restauranteAtualizado = restauranteDesatualizado.toBuilder().nome("Jardins Grill Prime").build();

        var resultado = restauranteGateway.update(restauranteAtualizado);

        assertThat(resultado)
                .isInstanceOf(Restaurante.class)
                .isNotNull();

        assertNotEquals(resultado.getNome(), nomeAntigo);
        assertEquals(resultado.getId(), restauranteDesatualizado.getId());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(restauranteAtualizado);
    }

    @Nested
    class ListarRestaurante {
        @Test
        void devePermitirBuscarRestaurante() {
            var restaurante = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));

            var restauranteOptional = restauranteGateway.findById(restaurante.getId());

            assertThat(restauranteOptional)
                    .isPresent()
                    .containsSame(restaurante);
            restauranteOptional.ifPresent(restauranteObtido -> {
                assertThat(restauranteObtido)
                        .usingRecursiveComparison()
                        .isEqualTo(restaurante);
            });
        }

        @Test
        void devePermitirListarRestaurantePorNome() {
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));

            var resultado = restauranteGateway.findByNomeContaining("grill");

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);

        }

        @Test
        void devePermitirListarRestaurantePorTipoCulinaria() {
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));

            var resultado = restauranteGateway.findByTipoCulinariaContaining("steakhouse");

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);

        }

        @Test
        void devePermitirListarRestaurantePorEndereco() {
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));
            var restaurante3 = RestauranteHelper.registrarRestaurante(restauranteRepository, RestauranteHelper.gerarRestaurante(null));

            Endereco endereco = Endereco.builder()
                    .cidade("Umuarama")
                    .estado("PR")
                    .build();
            var resultado = restauranteGateway.findByEndereco(endereco);

            assertThat(resultado)
                    .hasSize(3)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2, restaurante3);
        }
    }
}
