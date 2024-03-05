package com.fase2.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
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
    void devePermitirConsultarRestaurante(){
        var restaurante = RestauranteHelper.registrarRestaurante(restauranteRepository);

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
    void devePermitirApagarRestaurante() {
        var restaurante = RestauranteHelper.registrarRestaurante(restauranteRepository);
        var id = restaurante.getId();

        restauranteRepository.deleteById(id);

        var restauranteOptional = restauranteRepository.findById(id);

        assertThat(restauranteOptional)
                .isEmpty();
    }

    @Test
    void devePermitirListarRestaurantes(){
        var restaurante1 = RestauranteHelper.registrarRestaurante(restauranteRepository);
        var restaurante2 = RestauranteHelper.registrarRestaurante(restauranteRepository);
        var restaurante3 = RestauranteHelper.registrarRestaurante(restauranteRepository);

        var resultado = restauranteRepository.findAll();

        assertThat(resultado)
                .hasSize(3);
    }



}
