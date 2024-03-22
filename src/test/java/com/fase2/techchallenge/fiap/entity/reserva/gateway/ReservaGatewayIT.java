package com.fase2.techchallenge.fiap.entity.reserva.gateway;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ReservaGatewayIT {

    @Autowired
    ReservaGateway reservaGateway;

    @Test
    void devePermitirCriar(){
        //Arrange
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataFinal, "RESERVADO");
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

        //Act
        var resultado = reservaGateway.findById(reserva.getId());

        //Assert
        assertThat(resultado).isInstanceOf(Optional.of(reserva).getClass());
    }

    @Test
    void devePermitirBuscar_PeloMesaIdRestauranteDataHoraInicio() {
        //Arrange
        Restaurante restaurante = RestauranteHelper.gerarRestaurante(1L);
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);

        //Act
        var resultado = reservaGateway.findByMesaIdRestauranteAndDataHoraInicioBetween(restaurante, dataInicio, dataFinal);

        //Assert
        assertThat(resultado).isInstanceOf(List.class);
    }

    @Test
    void devePermitirBuscar_PelaSituacaoECliente() {
        //Arrange
        String situacao = "RESERVADO";

        Cliente cliente = new Cliente();
        cliente.setEmail("joao.silva@example.com");

        Pageable pageable = Pageable.unpaged();

        //Act
        Page<Reserva> resultado = reservaGateway.findBySituacaoAndCliente(situacao, cliente, pageable);

        //Assert
        assertThat(resultado).isInstanceOf(Page.class).isNotNull();
    }

    @Test
    void devePermitirBuscar_PeloIdMesaESituacaoEntreDataHoraInicioEDataHoraFinal()  {
        //Arrange
        Long idMesa = 1L;
        String situacao = "RESERVADO";
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);

        //Act
        List<Reserva> resultado = reservaGateway.findByIdMesaAndSituacaoAndDataHoraInicioBetweenOrDataHoraFinalBetween(idMesa, situacao, dataInicio, dataFinal);

        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(List.class);
    }
}
