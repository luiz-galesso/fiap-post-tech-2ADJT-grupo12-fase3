package com.fase2.techchallenge.fiap.infrastructure.reserva.repository;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ReservaRepositoryIT
{
    @Autowired
    ReservaRepository reservaRepository;

    @Test
    void devePermitirBuscarMesaPorIdRestauranteEntreDataHoraInicioEDataHoraFim() {
        //Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        var reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataFinal, "RESERVADO");
        var reservaSalva = reservaRepository.save(reserva);
        //Act
        var resultado = reservaRepository.findByMesaIdRestauranteAndDataHoraInicioBetween(restaurante, dataInicio, dataFinal);
        //Assert
        assertThat(resultado).isInstanceOf(List.class);
        assertThat(resultado).isNotEmpty();
    }

    @Test
    void devePermitirBuscarPorSituacaoECliente() {
        //Arrange
        Cliente cliente = new Cliente();
        cliente.setEmail("joao.silva@example.com");

        Pageable pageable = Pageable.unpaged();

        String situacao = "RESERVADO";
        //Act
        var resultado = reservaRepository.findBySituacaoAndCliente(situacao, cliente, pageable);
        //Assert
        assertThat(resultado).isInstanceOf(Page.class);
        assertThat(resultado.getContent().size()).isZero();
    }

    @Test
    void devePermitirListarReservasExistentes() {
        //Arrange
        Long idMesa = 1L;
        String situacao = "RESERVADO";
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        //Act
        var resultado = reservaRepository.reservaExists(idMesa, situacao, dataInicio, dataFinal);
        //Assert
        assertThat(resultado).isInstanceOf(List.class);
        assertThat(resultado.size()).isZero();
    }
}
