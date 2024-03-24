package com.fase2.techchallenge.fiap.usecase.restaurante;

import com.fase2.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AtualizarRestauranteTest {
    @Mock
    RestauranteGateway restauranteGateway;
    AutoCloseable openMocks;
    private AtualizarRestaurante atualizarRestaurante;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        atualizarRestaurante = new AtualizarRestaurante(restauranteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class AtualizarRestauranteTestes {
        @Test
        void devePermitirAtualizarRestaurante() {
            var restauranteDesatualizado = RestauranteHelper.gerarRestaurante(1L);
            var restauranteDesatualizadoSituacao = restauranteDesatualizado.getSituacao();
            var restauranteAtualizado = RestauranteHelper.gerarRestaurante(1L);
            restauranteAtualizado.setSituacao("INATIVO");
            var restauranteInsertDTO = new RestauranteInsertDTO(restauranteDesatualizado.getNome()
                    , restauranteDesatualizado.getCnpj()
                    , restauranteDesatualizado.getEndereco()
                    , restauranteDesatualizado.getTipoCulinaria()
                    , restauranteDesatualizado.getCapacidade()
                    , restauranteAtualizado.getSituacao()
                    , restauranteDesatualizado.getHorarioFuncionamento());

            when(restauranteGateway.findById(any(Long.class))).thenReturn(Optional.of(restauranteDesatualizado));

            when(restauranteGateway.update(any(Restaurante.class))).thenReturn(restauranteAtualizado);

            var resultado = atualizarRestaurante.execute(restauranteDesatualizado.getId(), restauranteInsertDTO);

            assertThat(resultado)
                    .isInstanceOf(Restaurante.class)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(restauranteAtualizado);
            assertThat(resultado.getSituacao())
                    .isNotEqualTo(restauranteDesatualizadoSituacao)
                    .isNotNull();

            verify(restauranteGateway, times(1))
                    .findById(any(Long.class));
            verify(restauranteGateway, times(1))
                    .update(any(Restaurante.class));


        }

        @Test
        void naoDeveAtualizarSeNaoEncontrarRestaurante() {
            var restauranteDesatualizado = RestauranteHelper.gerarRestaurante(1L);
            var restauranteInsertDTO = new RestauranteInsertDTO();

            when(restauranteGateway.findById(any(Long.class))).thenReturn(Optional.empty());

            var resultado = atualizarRestaurante.execute(restauranteDesatualizado.getId(), restauranteInsertDTO);

            assertThat(resultado)
                    .isNull();

            verify(restauranteGateway, times(1))
                    .findById(any(Long.class));
            verify(restauranteGateway, never())
                    .update(any(Restaurante.class));

        }
    }
}
