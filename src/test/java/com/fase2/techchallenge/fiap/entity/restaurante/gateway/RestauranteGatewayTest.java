package com.fase2.techchallenge.fiap.entity.restaurante.gateway;


import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteGatewayTest {

    private RestauranteGateway restauranteGateway;

    @Mock
    private RestauranteRepository restauranteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        restauranteGateway = new RestauranteGateway(restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarRestaurante(){
        Restaurante restaurante = RestauranteHelper.gerarRestaurante(null);

        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        var resultado = restauranteGateway.create(restaurante);

        verify(restauranteRepository, times(1)).save(restaurante);
        assertThat(resultado)
                .isInstanceOf(Restaurante.class)
                .isNotNull();
        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(restaurante);
    }

    @Test
    void devePermitirAlterarRestaurante() {
        Restaurante restauranteDesatualizado = RestauranteHelper.gerarRestaurante(1L);
        var restauranteAtualizado = RestauranteHelper.gerarRestaurante(1L);
        restauranteAtualizado.setNome("Jardins Grill Prime");

        when(restauranteRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(restauranteDesatualizado));
        when(restauranteRepository.save(any(Restaurante.class)))
                .thenAnswer(i -> i.getArgument(0));

        var resultado = restauranteGateway.update(restauranteAtualizado);

        assertThat(resultado)
                .isInstanceOf(Restaurante.class)
                .isNotNull();

        assertNotEquals(resultado.getNome(),restauranteDesatualizado.getNome());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(restauranteAtualizado);
        verify(restauranteRepository,times(1)).save(any(Restaurante.class));
    }

    @Nested
    class ListarRestaurante {
        @Test
        void devePermitirBuscarRestaurante() {
            Restaurante restaurante = RestauranteHelper.gerarRestaurante(1L);
            when(restauranteRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(restaurante));

            var resultado = restauranteGateway.findById(any(Long.class));

            verify(restauranteRepository, times(1))
                    .findById(any(Long.class));
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(restaurante, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(restaurante));
        }

        @Test
        void devePermitirListarRestaurantePorNome() {
            Restaurante restaurante1 = RestauranteHelper.gerarRestaurante(1L);
            Restaurante restaurante2 = RestauranteHelper.gerarRestaurante(2L);
            var restauranteList = Arrays.asList(restaurante1, restaurante2);

            when(restauranteRepository.findByNomeContainingIgnoreCase(any(String.class)))
                    .thenReturn(restauranteList);

            var resultado = restauranteGateway.findByNomeContaining("Grill");

            verify(restauranteRepository, times(1))
                    .findByNomeContainingIgnoreCase(any(String.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }

        @Test
        void devePermitirListarRestaurantePorTipoCulinaria() {
            Restaurante restaurante1 = RestauranteHelper.gerarRestaurante(1L);
            Restaurante restaurante2 = RestauranteHelper.gerarRestaurante(2L);
            var restauranteList = Arrays.asList(restaurante1, restaurante2);

            when(restauranteRepository.findByTipoCulinariaContainingIgnoreCase(any(String.class)))
                    .thenReturn(restauranteList);

            var resultado = restauranteGateway.findByTipoCulinariaContaining("BBQ");

            verify(restauranteRepository, times(1))
                    .findByTipoCulinariaContainingIgnoreCase(any(String.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }

        @Test
        void devePermitirListarRestaurantePorEndereco() {
            Endereco endereco = Endereco.builder()
                    .logradouro("Av. Silva Jardim")
                    .cidade("Umuarama")
                    .cep(82887417L)
                    .numero("780A").build();

            Restaurante restaurante1 = RestauranteHelper.gerarRestaurante(1L);
            Restaurante restaurante2 = RestauranteHelper.gerarRestaurante(2L);
            Restaurante restauranteBusca = new Restaurante(endereco);

            var restauranteList = Arrays.asList(restaurante1, restaurante2);

            when(restauranteRepository.findAll(Example.of(restauranteBusca)))
                    .thenReturn(restauranteList);

            var resultado = restauranteGateway.findByEndereco(endereco);

            verify(restauranteRepository, times(1))
                    .findAll(Example.of(restauranteBusca));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }
    }






}

