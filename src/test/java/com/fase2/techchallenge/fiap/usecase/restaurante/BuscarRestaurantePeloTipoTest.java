package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
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

public class BuscarRestaurantePeloTipoTest {
    @Mock
    RestauranteGateway restauranteGateway;
    AutoCloseable openMocks;
    private BuscarRestaurantePeloTipo buscarRestaurantePeloTipo;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscarRestaurantePeloTipo = new BuscarRestaurantePeloTipo(restauranteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantePeloTipo() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante2 = RestauranteHelper.gerarRestaurante(null);
            var ListaRestaurante = Arrays.asList(restaurante1, restaurante2);


            when(restauranteGateway.findByTipoCulinariaContaining(any(String.class))).thenReturn(ListaRestaurante);

            var resultado = buscarRestaurantePeloTipo.execute("steakhouse");

            verify(restauranteGateway, times(1))
                    .findByTipoCulinariaContaining(any(String.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);

        }

        @Test
        void deveBuscarRestaurantesPeloTipoSemResultados(){
            when(restauranteGateway.findByTipoCulinariaContaining(any(String.class))).thenReturn(Collections.emptyList());

            var resultado = buscarRestaurantePeloTipo.execute("<tipoInvalido>");

            assertThat(resultado)
                    .isEmpty();
            verify(restauranteGateway, times(1))
                    .findByTipoCulinariaContaining(any(String.class));
        }

    }
}
