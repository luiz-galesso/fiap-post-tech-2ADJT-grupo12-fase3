package com.fase3.techchallenge.fiap.usecase.restaurante;

import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
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
public class CriarRestauranteIT {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CriarRestaurante criarRestaurante;

    @Nested
    class CriacaoRestaurante {
        @Test
        void deveExecutarCriacaoDeRestaurante() {
            var restaurante = RestauranteHelper.gerarRestaurante(null);
            RestauranteInsertDTO restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome()
                    , restaurante.getCnpj()
                    , restaurante.getEndereco()
                    , restaurante.getTipoCulinaria()
                    , restaurante.getCapacidade()
                    , restaurante.getSituacao()
                    , restaurante.getHorarioFuncionamento());

            var restauranteCriado = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteCriado)
                    .isInstanceOf(Restaurante.class)
                    .isNotNull();
            assertThat(restauranteCriado)
                    .usingRecursiveComparison()
                    .ignoringFields("dataRegistro", "id")
                    .isEqualTo(restaurante);
            assertThat(restauranteCriado.getId())
                    .isNotNull();

        }

        @Test
        void deveCriarRestauranteAtivoQuandoSituacaoNula() {
            var restaurante = RestauranteHelper.gerarRestaurante(null);
            RestauranteInsertDTO restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome()
                    , restaurante.getCnpj()
                    , restaurante.getEndereco()
                    , restaurante.getTipoCulinaria()
                    , restaurante.getCapacidade()
                    , null
                    , restaurante.getHorarioFuncionamento());

            var restauranteCriado = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteCriado.getSituacao())
                    .isEqualTo("ATIVO");

        }

        @Test
        void deveCriarRestauranteAtivoQuandoSituacaoDiferenteDeAtivo() {
            var restaurante = RestauranteHelper.gerarRestaurante(null);
            RestauranteInsertDTO restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome()
                    , restaurante.getCnpj()
                    , restaurante.getEndereco()
                    , restaurante.getTipoCulinaria()
                    , restaurante.getCapacidade()
                    , "INATIVO"
                    , restaurante.getHorarioFuncionamento());

            var restauranteCriado = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteCriado.getSituacao())
                    .isEqualTo("ATIVO")
                    .isNotEqualTo(restauranteInsertDTO.getSituacao());
        }

        @Test
        void deveCriarRestauranteComDataRegistro() {
            var restaurante = RestauranteHelper.gerarRestaurante(null);
            RestauranteInsertDTO restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome()
                    , restaurante.getCnpj()
                    , restaurante.getEndereco()
                    , restaurante.getTipoCulinaria()
                    , restaurante.getCapacidade()
                    , restaurante.getSituacao()
                    , restaurante.getHorarioFuncionamento());

            var restauranteCriado = criarRestaurante.execute(restauranteInsertDTO);

            assertThat(restauranteCriado.getDataRegistro())
                    .isNotNull()
                    .isNotEqualTo(restaurante.getDataRegistro());
        }
    }


}
