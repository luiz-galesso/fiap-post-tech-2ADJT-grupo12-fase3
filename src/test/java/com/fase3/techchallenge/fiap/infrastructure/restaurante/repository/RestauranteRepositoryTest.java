package com.fase3.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteRepositoryTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    AutoCloseable openMocks;
    @BeforeEach
    void setUp() { openMocks = MockitoAnnotations.openMocks(this);    }
    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    void devePermitirRegistrarRestaurante() {
        Restaurante restaurante = RestauranteHelper.gerarRestaurante(null);
        when(restauranteRepository.save(any(Restaurante.class))).thenAnswer(a -> a.getArguments()[0]);

        var restauranteArmazenado     = restauranteRepository.save(restaurante);
        verify(restauranteRepository, times(1)).save(restaurante);
        assertThat(restauranteArmazenado)
                .isInstanceOf(Restaurante.class)
                .isNotNull();
        assertThat(restauranteArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(restaurante);
    }
    @Test
    void devePermitirApagarRestaurante() {
        Long id = new Random().nextLong();
        doNothing().when(restauranteRepository).deleteById(id);

        restauranteRepository.deleteById(id);

        verify(restauranteRepository,times(1)).deleteById(id);

    }
    @Nested
    class ConsultarRestaurantes {
        @Test
        void devePermitirConsultarRestaurante() {

            Long id = 1L;

            var restaurante = RestauranteHelper.gerarRestaurante(id);

            when(restauranteRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurante));

            var restauranteOptional = restauranteRepository.findById(id);

            verify(restauranteRepository,times(1)).findById(id);
            assertThat(restauranteOptional)
                    .isPresent()
                    .containsSame(restaurante)
            ;
            restauranteOptional.ifPresent(restauranteArmazenado -> {
                assertThat(restauranteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(restaurante);
            });
        }
        @Test
        void devePermitirListarRestaurantes() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante2 = RestauranteHelper.gerarRestaurante(null);
            var restauranteList = Arrays.asList(restaurante1, restaurante2);

            when(restauranteRepository.findAll()).thenReturn(restauranteList);

            var resultado = restauranteRepository.findAll();

            verify(restauranteRepository, times(1)).findAll();

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1, restaurante2);
        }

        @Test
        void devePermitirConsultarRestaurantePorEndereco() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante2 = RestauranteHelper.gerarRestaurante(null);
            var restauranteList = Arrays.asList(restaurante1,restaurante2);

            when(restauranteRepository.findByEndereco(any(String.class),any(String.class),any(Long.class),any(String.class))).thenReturn(restauranteList);

            var resultado = restauranteRepository.findByEndereco("Av. Silva Jardim", "Umuarama", 82887417L, "780A");

            verify(restauranteRepository,times(1)).findByEndereco(any(String.class),any(String.class),any(Long.class),any(String.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(restaurante1,restaurante2);
        }
        @Test
        void devePermitirConsultarRestaurantePorTipoCulinaria() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restaurante2 = RestauranteHelper.gerarRestaurante(null);
            var restaurante3 = RestauranteHelper.gerarRestaurante(null);
            var restauranteList = Arrays.asList(restaurante1,restaurante2,restaurante3);

            when(restauranteRepository.findByTipoCulinariaContainingIgnoreCase(any(String.class))).thenReturn(restauranteList);

            var resultado = restauranteRepository.findByTipoCulinariaContainingIgnoreCase("BBQ");

            verify(restauranteRepository,times(1)).findByTipoCulinariaContainingIgnoreCase(any(String.class));

            assertThat(resultado)
                    .hasSize(3)
                    .containsExactlyInAnyOrder(restaurante1,restaurante2,restaurante3);
        }

        @Test
        void devePermitirConsultarRestauranteQueNomeContenha() {
            var restaurante1 = RestauranteHelper.gerarRestaurante(null);
            var restauranteList = Arrays.asList(restaurante1);

            when(restauranteRepository.findByNomeContainingIgnoreCase(any(String.class))).thenReturn(restauranteList);

            var resultado = restauranteRepository.findByNomeContainingIgnoreCase("Sushi");

            verify(restauranteRepository,times(1)).findByNomeContainingIgnoreCase(any(String.class));

            assertThat(resultado)
                    .hasSize(1)
                    .containsExactlyInAnyOrder(restaurante1);
        }

    }



}
