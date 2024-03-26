package com.fase3.techchallenge.fiap.usecase.reserva;

import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class BuscarReservasAtivasPorClienteTest {

    @Mock
    BuscarReservasAtivasPorCliente buscarReservasAtivasPorCliente;

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
    void devePermitirBuscarReservasAtivasPorCliente() {
        //Arrange
        String idCliente = "maria.santos@example.com";
        Pageable pageable = Pageable.unpaged();

        List<Reserva> reservas = ReservaHelper.gerarLista();

        Page<Reserva> pages = new PageImpl<>(reservas);

        when(buscarReservasAtivasPorCliente.execute(any(String.class), any(Pageable.class))).thenReturn(pages);
        //Act
        var resultado = buscarReservasAtivasPorCliente.execute(idCliente, pageable);
        //Assert
        assertThat(resultado).isNotNull().isInstanceOf(Page.class);
    }
}
