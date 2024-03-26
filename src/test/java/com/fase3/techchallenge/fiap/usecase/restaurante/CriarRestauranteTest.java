package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.gateway.RestauranteGateway;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CriarRestauranteTest {
    @Mock
    RestauranteGateway restauranteGateway;
    AutoCloseable openMocks;
    private CriarRestaurante criarRestaurante;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        criarRestaurante = new CriarRestaurante(restauranteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CriacaoRestaurante {
        @Test
        void deveExecutarCriacaoDeRestaurante() {
            Restaurante restaurante = RestauranteHelper.gerarRestaurante(null);
            var restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome(),
                    restaurante.getCnpj(),
                    restaurante.getEndereco(),
                    restaurante.getTipoCulinaria(),
                    restaurante.getCapacidade(),
                    restaurante.getSituacao(),
                    restaurante.getHorarioFuncionamento());

            when(restauranteGateway.create(any(Restaurante.class))).thenReturn(restaurante);

            var restauranteInserido = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteInserido)
                    .isNotNull()

                    .isInstanceOf(Restaurante.class);

            assertThat(restauranteInserido)
                    .usingRecursiveComparison()
                    .isEqualTo(restaurante);

            verify(restauranteGateway, times(1)).create(any(Restaurante.class));
        }

        @Test
        void deveCriarRestauranteAtivoQuandoSituacaoNula() {
            Restaurante restaurante = RestauranteHelper.gerarRestaurante(null);

            var restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome(),
                    restaurante.getCnpj(),
                    restaurante.getEndereco(),
                    restaurante.getTipoCulinaria(),
                    restaurante.getCapacidade(),
                    null,
                    restaurante.getHorarioFuncionamento());

            when(restauranteGateway.create(any(Restaurante.class))).thenReturn(restaurante);

            var restauranteInserido = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteInserido.getSituacao()).isEqualTo("ATIVO");
            assertThat(restauranteInsertDTO.getSituacao()).isNotEqualTo(restauranteInserido.getSituacao());

        }

        @Test
        void deveCriarRestauranteAtivoQuandoSituacaoDiferenteDeAtivo() {
            Restaurante restaurante = RestauranteHelper.gerarRestaurante(null);

            var restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome(),
                    restaurante.getCnpj(),
                    restaurante.getEndereco(),
                    restaurante.getTipoCulinaria(),
                    restaurante.getCapacidade(),
                    "INATIVO",
                    restaurante.getHorarioFuncionamento());

            when(restauranteGateway.create(any(Restaurante.class))).thenReturn(restaurante);

            var restauranteInserido = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteInserido.getSituacao()).isEqualTo("ATIVO");
            assertThat(restauranteInsertDTO.getSituacao()).isNotEqualTo(restauranteInserido.getSituacao());

        }
    }
}
