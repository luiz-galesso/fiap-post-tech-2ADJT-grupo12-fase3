package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BuscarRestaurantePeloNomeTest {
    @Mock
    RestauranteGateway restauranteGateway;
    AutoCloseable openMocks;
    private BuscarRestaurantePeloNome buscarRestaurantePeloNome;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscarRestaurantePeloNome = new BuscarRestaurantePeloNome(restauranteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantePeloNome() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante2 = RestauranteHelper.gerarRestaurante(null);
            var ListaRestaurante = Arrays.asList(restaurante1, restaurante2);


            when(restauranteGateway.findByNomeContaining(any(String.class))).thenReturn(ListaRestaurante);

            var resultado = buscarRestaurantePeloNome.execute("BBQ");

            verify(restauranteGateway, times(1))
                    .findByNomeContaining(any(String.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);

        }

        @Test
        void deveBuscarRestaurantesPeloNomeSemResultados() {
            when(restauranteGateway.findByNomeContaining(any(String.class))).thenReturn(Collections.emptyList());

            var resultado = buscarRestaurantePeloNome.execute("<tipoInvalido>");

            assertThat(resultado)
                    .isEmpty();
            verify(restauranteGateway, times(1))
                    .findByNomeContaining(any(String.class));
        }

    }
}
