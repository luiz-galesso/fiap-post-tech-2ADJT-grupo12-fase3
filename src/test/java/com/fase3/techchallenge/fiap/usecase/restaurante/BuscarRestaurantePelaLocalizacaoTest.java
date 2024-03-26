package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
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

public class BuscarRestaurantePelaLocalizacaoTest {
    @Mock
    RestauranteGateway restauranteGateway;
    AutoCloseable openMocks;
    private BuscarRestaurantePelaLocalizacao buscarRestaurantePelaLocalizacao;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscarRestaurantePelaLocalizacao = new BuscarRestaurantePelaLocalizacao(restauranteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantePelaLocalizacao() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante2 = RestauranteHelper.gerarRestaurante(null);
            var ListaRestaurante = Arrays.asList(restaurante1, restaurante2);
            Endereco enderecoBusca = new Endereco();
            enderecoBusca.setCidade("Umuarama");
            enderecoBusca.setEstado("PR");


            when(restauranteGateway.findByEndereco(any(Endereco.class))).thenReturn(ListaRestaurante);

            var resultado = buscarRestaurantePelaLocalizacao.execute(enderecoBusca);

            verify(restauranteGateway, times(1))
                    .findByEndereco(any(Endereco.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);

        }

        @Test
        void deveBuscarRestaurantesPelaLocalizacaoSemResultados() {
            when(restauranteGateway.findByEndereco(any(Endereco.class))).thenReturn(Collections.emptyList());

            var resultado = buscarRestaurantePelaLocalizacao.execute(new Endereco());

            assertThat(resultado)
                    .isEmpty();
            verify(restauranteGateway, times(1))
                    .findByEndereco(any(Endereco.class));
        }

    }
}
