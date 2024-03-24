package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ObterRestaurantePeloIdTest {
    @Mock
    RestauranteGateway restauranteGateway;
    AutoCloseable openMocks;
    private ObterRestaurantePeloId obterRestaurantePeloId;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterRestaurantePeloId = new ObterRestaurantePeloId(restauranteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ObterRestaurante{
        @Test
        void deveObterRestaurantePorId(){
            var restaurante = RestauranteHelper.gerarRestaurante(null);

            when(restauranteGateway.findById(any(Long.class))).thenReturn(Optional.of(restaurante));

            var restauranteObtido = obterRestaurantePeloId.execute(any(Long.class));

            assertThat(restauranteObtido)
                    .isNotNull()
                    .isInstanceOf(Restaurante.class)
                    .usingRecursiveComparison()
                    .isEqualTo(restaurante);
        }

        @Test
        void deveGerarErroQuandoNaoEncontrarRestaurantePorId(){
            when(restauranteGateway.findById(any(Long.class))).thenReturn(Optional.empty());
            assertThatThrownBy(() -> obterRestaurantePeloId.execute(any(Long.class)))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Restaurante não localizado");
            verify(restauranteGateway, times(1)).findById(any(Long.class));
        }
    }
}
