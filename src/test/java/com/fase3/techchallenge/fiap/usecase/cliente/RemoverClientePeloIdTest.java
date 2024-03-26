package com.fase3.techchallenge.fiap.usecase.cliente;

import com.fase3.techchallenge.fiap.entity.cliente.gateway.ClienteGateway;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RemoverClientePeloIdTest {
    @Mock
    ClienteGateway clienteGateway;
    RemoverClientePeloId removerClientePeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        removerClientePeloId = new RemoverClientePeloId(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRemoverCliente() {
        var id = "eduardo.melo@example.com";
        var cliente = new Cliente();

        when(clienteGateway.findById(id)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteGateway).remove(id);

        var retorno = removerClientePeloId.execute(id);

        assertThat(retorno).isTrue();
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, times(1)).remove(any(String.class));
    }


    @Test
    void deveGerarExcecao_QuandoRemoverCliente_IdNaoExiste() {
        var id = "francisco-filho@email.com";

        when(clienteGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> removerClientePeloId.execute(id)).isInstanceOf(BussinessErrorException.class).hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, never()).remove(any(String.class));
    }

}
