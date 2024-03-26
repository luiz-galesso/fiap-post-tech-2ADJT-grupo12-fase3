package com.fase3.techchallenge.fiap.entity.reserva.gateway;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservaGatewayTest {
    @Mock
    ReservaGateway reservaGateway;

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
    void devePermitirCriar(){
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataFinal, "RESERVADO");
        
        when(reservaGateway.create(any(Reserva.class))).thenReturn(reserva);
        //Act
        var resultado = reservaGateway.create(reserva);
        //Assert
        assertThat(resultado).isInstanceOf(Reserva.class);
        assertThat(resultado.getId()).isNotNull();
    }

    @Test
    void devePermitirAtualizar(){
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataFinal, "RESERVADO");

        when(reservaGateway.update(any(Reserva.class))).thenReturn(reserva);
        //Act
        var resultado = reservaGateway.update(reserva);
        //Assert
        assertThat(resultado).isInstanceOf(Reserva.class);
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getId()).isEqualTo(reserva.getId());
    }

    @Test
    void devePermitirBuscar_PeloID(){
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataFinal, "RESERVADO");

        when(reservaGateway.findById(any(Long.class))).thenReturn(Optional.of(reserva));

        //Act
        var resultado = reservaGateway.findById(reserva.getId());

        //Assert
        assertThat(resultado).isInstanceOf(Optional.of(reserva).getClass());
        assertThat(resultado.isPresent()).isEqualTo(true);
    }

    @Test
    void devePermitirBuscar_PeloMesaIdRestauranteDataHoraInicio() {
        //Arrange
        Restaurante restaurante = RestauranteHelper.gerarRestaurante(1L);
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);

        List<Reserva> reservas = ReservaHelper.gerarLista();

        when(reservaGateway
                .findByMesaIdRestauranteAndDataHoraInicioBetween(any(Restaurante.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservas);

        //Act
        var resultado = reservaGateway.findByMesaIdRestauranteAndDataHoraInicioBetween(restaurante, dataInicio, dataFinal);

        //Assert
        assertThat(resultado).isInstanceOf(List.class);
        assertThat(resultado.size()).isEqualTo(reservas.size());
    }

    @Test
    void devePermitirBuscar_PelaSituacaoECliente() {
        //Arrange
        String situacao = "RESERVADO";

        Cliente cliente = new Cliente();
        cliente.setEmail("joao.silva@example.com");

        List<Reserva> reservas = ReservaHelper.gerarLista();

        Pageable pageable = Pageable.unpaged();
        Page<Reserva> page = new PageImpl<>(reservas);

        when(reservaGateway.findBySituacaoAndCliente(any(String.class), any(Cliente.class), any(Pageable.class))).thenReturn(page);

        //Act
        Page<Reserva> resultado = reservaGateway.findBySituacaoAndCliente(situacao, cliente, pageable);

        //Assert
        verify(reservaGateway, times(1)).findBySituacaoAndCliente(situacao, cliente, pageable);
        Assertions.assertEquals(reservas.size(), resultado.getContent().size());
    }

    @Test
    void devePermitirBuscar_PeloIdMesaESituacaoEntreDataHoraInicioEDataHoraFinal()  {
        //Arrange
        Long idMesa = 1L;
        String situacao = "RESERVADO";
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);

        List<Reserva> reservas = ReservaHelper.gerarLista();

        when(reservaGateway.findByIdMesaAndSituacaoAndDataHoraInicioBetweenOrDataHoraFinalBetween(idMesa, situacao, dataInicio, dataFinal)).thenReturn(reservas);

        //Act
        List<Reserva> resultado = reservaGateway.findByIdMesaAndSituacaoAndDataHoraInicioBetweenOrDataHoraFinalBetween(idMesa, situacao, dataInicio, dataFinal);

        //Assert
        verify(reservaGateway, times(1)).findByIdMesaAndSituacaoAndDataHoraInicioBetweenOrDataHoraFinalBetween(idMesa, situacao, dataInicio, dataFinal);
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(reservas.size());
    }
}
