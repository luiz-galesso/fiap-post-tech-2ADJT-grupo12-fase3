package com.fase2.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    void devePermitirApagarRestaurante() {
        Long id = new Random().nextLong();
        doNothing().when(restauranteRepository).deleteById(id);

        restauranteRepository.deleteById(id);

        verify(restauranteRepository,times(1)).deleteById(id);

    }
    @Test
    void devePermitirListarRestaurantes() {
        var restaurante1 = RestauranteHelper.gerarRestaurante(null);
        var restaurante2 = RestauranteHelper.gerarRestaurante(null);
        var restauranteList = Arrays.asList(restaurante1,restaurante2);

        when(restauranteRepository.findAll()).thenReturn(restauranteList);

        var resultado = restauranteRepository.findAll();

        verify(restauranteRepository, times(1)).findAll();

        assertThat(resultado)
                .hasSize(2)
                .containsExactlyInAnyOrder(restaurante1,restaurante2);
    }


}
