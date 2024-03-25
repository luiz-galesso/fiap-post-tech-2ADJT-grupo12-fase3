package com.fase2.techchallenge.fiap.usecase.cliente;

import com.fase2.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.utils.ClienteHelper;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ObterClientePeloIdTest {
    @Mock
    ClienteGateway clienteGateway;
    ObterClientePeloId obterClientePeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterClientePeloId = new ObterClientePeloId(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarCliente() {
        var cliente = ClienteHelper.gerarCliente("eduardo.melo@example.com", "Eduardo Melo", "ATIVO", LocalDate.of(1990, 05, 19));
        when(clienteGateway.findById(cliente.getEmail())).thenReturn(Optional.of(cliente));

        var clienteRetornado = obterClientePeloId.execute(cliente.getEmail());

        assertThat(clienteRetornado).isNotNull().isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isEqualTo(cliente.getEmail());
        verify(clienteGateway, times(1)).findById(any(String.class));
    }

    @Test
    void deveGerarExcecao_QuandoBuscarCliente_NaoLocalizado() {
        var id = "joao-wick@email.com";

        when(clienteGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> obterClientePeloId.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não localizado");
        verify(clienteGateway, times(1)).findById(any(String.class));
    }
}
