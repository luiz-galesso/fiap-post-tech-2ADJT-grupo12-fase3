package com.fase2.techchallenge.fiap.infrastructure.restaurante.controller;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase2.techchallenge.fiap.handler.GlobalExceptionHandler;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteUpdateDTO;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import com.fase2.techchallenge.fiap.usecase.restaurante.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RestauranteControllerTest {
    @Mock
    BuscarRestaurantePeloNome buscarRestaurantePeloNome;
    @Mock
    BuscarRestaurantePeloTipo buscarRestaurantePeloTipo;
    @Mock
    BuscarRestaurantePelaLocalizacao buscarRestaurantePelaLocalizacao;
    AutoCloseable mock;
    @Mock
    private CriarRestaurante criarRestaurante;
    @Mock
    private AtualizarRestaurante atualizarRestaurante;
    @Mock
    private ObterRestaurantePeloId obterRestaurantePeloId;
    private MockMvc mockMvc;

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        RestauranteController restauranteController = new RestauranteController(criarRestaurante,
                obterRestaurantePeloId,
                buscarRestaurantePeloNome,
                buscarRestaurantePeloTipo,
                buscarRestaurantePelaLocalizacao,
                atualizarRestaurante);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CriacaoRestaurante {
        @Test
        void devePermitirCriarRestaurante() throws Exception {
            var restaurante = RestauranteHelper.gerarRestaurante(null);
            var restauranteInsertDTO = new RestauranteUpdateDTO(restaurante.getNome()
                    , restaurante.getCnpj()
                    , restaurante.getEndereco()
                    , restaurante.getTipoCulinaria()
                    , restaurante.getCapacidade()
                    , restaurante.getSituacao()
                    , restaurante.getHorarioFuncionamento());

            when(criarRestaurante.execute(any(RestauranteInsertDTO.class))).thenReturn(restaurante);

            mockMvc.perform(post("/restaurantes").content(asJsonString(restauranteInsertDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

            verify(criarRestaurante, times(1)).execute(any(RestauranteInsertDTO.class));
        }
    }

    @Nested
    class BuscarRestaurante {
        @Test
        void devePermitirBuscarRestaurantePeloId() throws Exception {
            var restaurante = RestauranteHelper.gerarRestaurante(1L);
            when(obterRestaurantePeloId.execute(any(Long.class))).thenReturn(restaurante);

            mockMvc.perform(get("/restaurantes/{id}", restaurante.getId())).andExpect(status().isOk());

            verify(obterRestaurantePeloId, times(1)).execute(any(Long.class));
        }

        @Test
        void deveGerarExcecaoAoBuscarRestaurantePorIdQueNaoExiste() throws Exception {
            when(obterRestaurantePeloId.execute(any(Long.class))).thenThrow(new EntityNotFoundException("Restaurante não localizado"));

            mockMvc.perform(get("/restaurantes/{id}", any(Long.class)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Restaurante não localizado"));
            verify(obterRestaurantePeloId, times(1)).execute(any(Long.class));
        }

        @Test
        void devePermitirListarRestaurantePeloNome() throws Exception {
            var restaurante1 = RestauranteHelper.gerarRestaurante(1L);
            var restaurante2 = RestauranteHelper.gerarRestaurante(2L);
            var restauranteList = Arrays.asList(restaurante1, restaurante2);

            when(buscarRestaurantePeloNome.execute(any(String.class))).thenReturn(restauranteList);

            mockMvc.perform(get("/restaurantes/busca-nome/{nome}", "Grill")).andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].nome").value(restaurante1.getNome()))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(2)));

            verify(buscarRestaurantePeloNome, times(1)).execute(any(String.class));
        }

        @Test
        void devePermitirListarRestaurantePeloTipo() throws Exception {
            var restaurante1 = RestauranteHelper.gerarRestaurante(1L);
            var restaurante2 = RestauranteHelper.gerarRestaurante(2L);
            var restauranteList = Arrays.asList(restaurante1, restaurante2);

            when(buscarRestaurantePeloTipo.execute(any(String.class))).thenReturn(restauranteList);

            mockMvc.perform(get("/restaurantes/busca-tipo-culinaria/{tipo}", "steakhouse")).andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].tipoCulinaria").value(restaurante1.getTipoCulinaria()))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(2)));

            verify(buscarRestaurantePeloTipo, times(1)).execute(any(String.class));
        }

        @Test
        void devePermitirListarRestaurantePeloEndereco() throws Exception {
            var restaurante1 = RestauranteHelper.gerarRestaurante(1L);
            var restaurante2 = RestauranteHelper.gerarRestaurante(2L);
            var restauranteList = Arrays.asList(restaurante1, restaurante2);
            var buscaEndereco = new Endereco();

            buscaEndereco.setCidade("Umuarama");
            buscaEndereco.setEstado("PR");

            when(buscarRestaurantePelaLocalizacao.execute(any(Endereco.class))).thenReturn(restauranteList);

            mockMvc.perform(get("/restaurantes/busca-endereco")
                            .param("cidade", buscaEndereco.getCidade())
                            .param("estado", buscaEndereco.getEstado()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].endereco.cidade").value(restaurante1.getEndereco().getCidade()))
                    .andExpect(jsonPath("$.[0].endereco.estado").value(restaurante1.getEndereco().getEstado()))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(2)));

            verify(buscarRestaurantePelaLocalizacao, times(1)).execute(any(Endereco.class));
        }
    }
    @Nested
    class AlterarRestaurante{
        @Test
        void devePermitirAlterarRestaurante() throws Exception {
            var restaurante = RestauranteHelper.gerarRestaurante(1L);

            when(atualizarRestaurante.execute(any(Long.class),any(RestauranteUpdateDTO.class))).thenReturn(restaurante);

            mockMvc.perform(put("/restaurantes/{id}",1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(restaurante))).andExpect(status().isOk());

        }
    }


}
