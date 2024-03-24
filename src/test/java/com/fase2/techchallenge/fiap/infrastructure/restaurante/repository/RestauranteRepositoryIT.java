package com.fase2.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class RestauranteRepositoryIT {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    void devePermitirCriarTabela(){
        Long totalTabelasCriadas = restauranteRepository.count();
        assertThat(totalTabelasCriadas).isNotNegative();
    }


    @Test
    void devePermitirRegistrarRestaurante(){
        Long id = 1L;
        var restaurante = RestauranteHelper.gerarRestaurante(id);
        var restauranteArmazenado = restauranteRepository.save(restaurante);

        assertThat(restauranteArmazenado)
                .isInstanceOf(Restaurante.class)
                .isNotNull();

        assertThat(restauranteArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(restaurante);

        assertThat(restauranteArmazenado.getId())
                .isNotNull();
    }
    @Test
    void devePermitirApagarRestaurante() {
        var restauranteGerado = RestauranteHelper.gerarRestaurante(null);
        var restaurante = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado);
        var id = restaurante.getId();

        restauranteRepository.deleteById(id);

        var restauranteOptional = restauranteRepository.findById(id);

        assertThat(restauranteOptional)
                .isEmpty();
    }


    @Nested
    class ConsultarRestaurantes {
        @Test
        void devePermitirListarRestaurantes() {
            var resultado = restauranteRepository.findAll();

            assertThat(resultado)
                    .hasSize(10);
        }
        @Test
        void devePermitirConsultarRestaurante() {
            var restauranteGerado = RestauranteHelper.gerarRestaurante(null);
            var restaurante = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado);

            var id = restaurante.getId();

            var restauranteOptional = restauranteRepository.findById(id);

            assertThat(restauranteOptional)
                    .isPresent()
                    .containsSame(restaurante);

            restauranteOptional.ifPresent(restauranteArmazenado -> {
                assertThat(restauranteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(restaurante);
            });

        }
        @Test
        void devePermitirConsultarRestauranteQueNomeContenha() {
            var restauranteGerado1 = RestauranteHelper.gerarRestaurante(null);
            restauranteGerado1.setNome("Avenida Grill");
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado1);

            var restauranteGerado2 = RestauranteHelper.gerarRestaurante(null);
            restauranteGerado2.setNome("Prime grill");
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado2);

            var restauranteGerado3 = RestauranteHelper.gerarRestaurante(null);
            restauranteGerado3.setNome("Sushi House");
            var restaurante3 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado3);

            var parteDoNome = "grill";

            var resultado = restauranteRepository.findByNomeContainingIgnoreCase(parteDoNome);

            assertThat(resultado).hasSize(2);
        }

        @Test
        void devePermitirConsultarRestaurantePorTipoCulinaria() {
            var restauranteGerado1 = RestauranteHelper.gerarRestaurante(null);
            restauranteGerado1.setTipoCulinaria("BBQ");
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado1);

            var restauranteGerado2 = RestauranteHelper.gerarRestaurante(null);
            restauranteGerado2.setTipoCulinaria("BBQ");
            var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado2);

            var restauranteGerado3 = RestauranteHelper.gerarRestaurante(null);
            restauranteGerado2.setTipoCulinaria("Oriental");
            var restaurante3 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado3);

            var tipoCulinariaEsperado = "Oriental";

            var resultado = restauranteRepository.findByTipoCulinariaContainingIgnoreCase("oriental");

            assertThat(resultado).hasSize(1);

        }

        @Test
        void devePermitirConsultarRestaurantePorEndereco() {
            var restauranteGerado1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository, restauranteGerado1);

            var resultado = restauranteRepository.findByEndereco("Av. Silva Jardim", "Umuarama", 82887417L, "780A");

            assertThat(resultado).hasSize(1);
        }
    }




}
