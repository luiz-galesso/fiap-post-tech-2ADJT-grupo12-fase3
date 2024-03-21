package com.fase2.techchallenge.fiap.infrastructure.reserva.repository;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservaRepositoryTest {

    @Mock
    private ReservaRepository reservaRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarMesaPorIdRestauranteEntreDataHoraInicioEDataHoraFim() {
        //Arrange
        var restaurante = RestauranteHelper.gerarRestaurante(1L);
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);

        List<Reserva> reservas = ReservaHelper.gerarLista();

        when(reservaRepository
                .findByMesaIdRestauranteAndDataHoraInicioBetween(any(Restaurante.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservas);

        //Act
        List<Reserva> resultado = reservaRepository.findByMesaIdRestauranteAndDataHoraInicioBetween(restaurante, dataInicio, dataFinal);

        //Assert
        Assertions.assertEquals(1, resultado.size());
    }

    @Test
    void devePermitirBuscarPorSituacaoECliente() {
        //Arrange
        String situacao = "RESERVADO";

        Cliente cliente = new Cliente();
        cliente.setEmail("joao.silva@example.com");

        List<Reserva> reservas = ReservaHelper.gerarLista();

        Pageable pageable = Pageable.unpaged();
        Page<Reserva> page = new PageImpl<>(reservas);

        when(reservaRepository.findBySituacaoAndCliente(any(String.class), any(Cliente.class), any(Pageable.class))).thenReturn(page);

        //Act
        Page<Reserva> resultado = reservaRepository.findBySituacaoAndCliente(situacao, cliente, pageable);

        //Assert
        verify(reservaRepository, times(1)).findBySituacaoAndCliente(situacao, cliente, pageable);
        Assertions.assertEquals(reservas.size(), resultado.getContent().size());

    }

    @Test
    void devePermitirListarReservasExistentes() {
        //Arrange
        Long idMesa = 1L;
        String situacao = "RESERVADO";
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);

        List<Reserva> reservas = ReservaHelper.gerarLista();

        when(reservaRepository.reservaExists(idMesa, situacao, dataInicio, dataFinal)).thenReturn(reservas);

        //Act
        List<Reserva> resultado = reservaRepository.reservaExists(idMesa, situacao, dataInicio, dataFinal);

        //Assert
        verify(reservaRepository, times(1)).reservaExists(idMesa, situacao, dataInicio, dataFinal);
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(reservas.size());
    }

}
