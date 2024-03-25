package com.fase2.techchallenge.fiap.infrastructure.cliente.controller;

import com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase2.techchallenge.fiap.utils.ClienteHelper;
import com.fase2.techchallenge.fiap.usecase.cliente.AtualizarCliente;
import com.fase2.techchallenge.fiap.usecase.cliente.CadastrarCliente;
import com.fase2.techchallenge.fiap.usecase.cliente.ObterClientePeloId;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest {
    @Mock
    CadastrarCliente cadastrarCliente;
    @Mock
    AtualizarCliente atualizarCliente;
    @Mock
    ObterClientePeloId obterClientePeloId;
    private MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ClienteController clienteController = new ClienteController(cadastrarCliente, atualizarCliente, obterClientePeloId);

        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RegistrarCliente {

        @Test
        void devePermitirCadastrarCliente() throws Exception {
            var cliente = ClienteHelper.gerarCliente("jarlan-silva@email.com", "Jarlan Silva", "ATIVO", LocalDate.of(1983, 12, 27));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());
            when(cadastrarCliente.execute(any(ClienteInsertDTO.class))).thenReturn(cliente);

            mockMvc.perform(post("/clientes")
                            .content(asJsonString(clienteInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            verify(cadastrarCliente, times(1)).execute(any(ClienteInsertDTO.class));
        }

        @Test
        void deveGerarExcecao_QuandoCadastrarCliente_JaCadastrado() throws Exception {
            var cliente = ClienteHelper.gerarCliente("eduardo.melo@example.com", "Eduardo Melo", "ATIVO", LocalDate.of(1983, 12, 27));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());
            var excecao = "Já existe um cliente cadastrado com o email informado.";
            when(cadastrarCliente.execute(clienteInsertDTO))
                    .thenThrow(new BussinessErrorException(excecao));

            mockMvc.perform(post("/clientes")
                            .content(asJsonString(clienteInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(excecao));

            verify(cadastrarCliente, times(1)).execute(any(ClienteInsertDTO.class));
        }

    }

    @Nested
    class BuscarCliente {

        @Test
        void devePermitirBuscarCliente() throws Exception {
            var cliente = ClienteHelper.gerarCliente("jarlan-silva@email.com", "Jarlan Silva", "ATIVO", LocalDate.of(1983, 12, 27));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());
            when(obterClientePeloId.execute(any(String.class))).thenReturn(cliente);

            mockMvc.perform(get("/clientes/{id}", cliente.getEmail()))
                    .andExpect(status()
                            .isOk());
            verify(obterClientePeloId, times(1)).execute(any(String.class));
        }

        @Test
        void deveGerarExcecao_QuandoBuscarCliente_NaoLocalizado() throws Exception {
            var id = "joao.wick";
            when(obterClientePeloId.execute(id)).thenThrow(EntityNotFoundException.class);

            mockMvc.perform(get("/clientes/{id}", id))
                    .andExpect(status()
                            .isBadRequest());
            verify(obterClientePeloId, times(1)).execute(id);
        }
    }

    @Nested
    class AlterarCliente {

        @Test
        void devePermitirAlterarCliente() throws Exception {
            var cliente = ClienteHelper.gerarCliente("eduardo.melo@example.com", "Eduardo Melo", "ATIVO", LocalDate.of(1993, 11, 02));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());
            when(atualizarCliente.execute(any(String.class), any(ClienteInsertDTO.class))).thenReturn(cliente);

            mockMvc.perform(put("/clientes/{id}", cliente.getEmail())
                            .content(asJsonString(clienteInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted());

            verify(atualizarCliente, times(1)).execute(any(String.class), any(ClienteInsertDTO.class));

        }

        @Test
        void deveGerarExcecao_QuandoAlterarCliente_ClienteNaoCadastrado() throws Exception {
            var cliente = ClienteHelper.gerarCliente("jarlan-silva@email.com", "Jarlan Silva", "ATIVO", LocalDate.of(1983, 12, 27));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());

            var excecao = "Não foi encontrado o cliente cadastrado com o email informado.";
            when(atualizarCliente.execute(cliente.getEmail(), clienteInsertDTO))
                    .thenThrow(new BussinessErrorException(excecao));

            mockMvc.perform(put("/clientes/{id}", cliente.getEmail())
                            .content(asJsonString(clienteInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(excecao));

            verify(atualizarCliente, times(1)).execute(any(String.class), any(ClienteInsertDTO.class));

        }
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }

}
