package com.fase3.techchallenge.fiap.infrastructure.reserva.controller;

import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase3.techchallenge.fiap.usecase.reserva.*;
import com.fase3.techchallenge.fiap.usecase.reserva.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservaControllerTest {
    @Mock
    private Reservar reservar;
    @Mock
    private ObterReservaPeloId obterReservaPeloId;
    @Mock
    private BuscarReservaPorRestauranteEData buscarReservaPorRestauranteEData;
    @Mock
    private RealizarCheckin realizarCheckin;
    @Mock
    private RealizarCheckout realizarCheckout;
    @Mock
    private CancelarReserva cancelarReserva;
    @Mock
    private BuscarReservasAtivasPorCliente buscarReservasAtivasPorCliente;

    private MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ReservaController reservaController = new ReservaController(reservar, obterReservaPeloId, buscarReservaPorRestauranteEData, realizarCheckin, realizarCheckout, cancelarReserva, buscarReservasAtivasPorCliente);
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RegistrarReserva {
        @Test
        void devePermitirRegistrarReserva() throws Exception {
            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);
            Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(reservaInsertDTO.getQuantidadeHoras()), "RESERVADO");
            when(reservar.execute(any(ReservaInsertDTO.class))).thenReturn(reserva);

            //Act
            mockMvc.perform(post("/reservas").content(asJsonString(reservaInsertDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

            //Assert
            verify(reservar, times(1)).execute(any(ReservaInsertDTO.class));

        }


    }

    @Nested
    class BuscarReserva {
        @Test
        void devePermitirBuscarReservaPeloId() throws Exception {
            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);
            Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(reservaInsertDTO.getQuantidadeHoras()), "RESERVADO");
            when(obterReservaPeloId.execute(any(Long.class))).thenReturn(reserva);

            //Act
            mockMvc.perform(get("/reservas/{id}", reserva.getId())).andExpect(status().isOk());

            //Assert
            verify(obterReservaPeloId, times(1)).execute(any(Long.class));

        }

        @Test
        void devePermitirBuscarReservaPeloRestauranteEData() throws Exception {
            //Arrange
            Long idRestaurante = 1L;
            LocalDate localDate = LocalDate.now();

            when(buscarReservaPorRestauranteEData.execute(any(Long.class), any(LocalDate.class))).thenReturn(new ArrayList<>());

            //Act
            mockMvc.perform(get("/reservas/restaurante/{idRestaurante}/data/{data}", idRestaurante, localDate)).andExpect(status().isOk());

            //Assert
            verify(buscarReservaPorRestauranteEData, times(1)).execute(any(Long.class), any(LocalDate.class));

        }

        /**
         * @todo mexer no método da controller
         */
        @Test
        void devePermitirBuscarReservasAtivasPorCliente() throws Exception {
            //Arrange
            String idCliente = "maria.santos@example.com";

            List<Reserva> reservas = ReservaHelper.gerarLista();

            Pageable pageable = PageRequest.of(0, 1); // Página 0, tamanho 1
            Page<Reserva> pages = new PageImpl<>(reservas, pageable, reservas.size());

            when(buscarReservasAtivasPorCliente.execute(any(String.class), any(Pageable.class))).thenReturn(pages);
            //Act

            mockMvc.perform(
                    get("/reservas/cliente/{idCliente}", idCliente)
                            .queryParam("page", String.valueOf(pageable.getPageNumber()))
                            .queryParam("size", String.valueOf(pageable.getPageSize()))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isOk());

            //Assert
            verify(buscarReservasAtivasPorCliente, times(1)).execute(any(String.class), any(Pageable.class));
        }
    }

    @Nested
    class AtualizarReserva {
        @Test
        void deveRealizarCheckin() throws Exception {
            //Arrange
            Long idReserva = 1L;
            String idCliente = "maria.santos@example.com";

            LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
            Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "CHECKIN");

            when(realizarCheckin.execute(any(Long.class), any(String.class))).thenReturn(reserva);
            //Act
            mockMvc.perform(put("/reservas/{idReserva}/checkin/{idCliente}", idReserva, idCliente)).andExpect(status().isOk());

            //Assert
            verify(realizarCheckin, times(1)).execute(any(Long.class), any(String.class));
        }

        @Test
        void deveRealizarCheckout() throws Exception {
            //Arrange
            Long idReserva = 1L;
            String idCliente = "maria.santos@example.com";

            LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
            Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "CHECKOUT");

            when(realizarCheckout.execute(any(Long.class), any(String.class))).thenReturn(reserva);
            //Act
            mockMvc.perform(put("/reservas/{idReserva}/checkout/{idCliente}", idReserva, idCliente)).andExpect(status().isOk());

            //Assert
            verify(realizarCheckout, times(1)).execute(any(Long.class), any(String.class));

        }

        @Test
        void devePermitirCancelarReserva() throws Exception {
            //Arrange
            Long idReserva = 1L;
            LocalDateTime dataInicio = LocalDateTime.now();
            Reserva reserva = ReservaHelper.gerarReserva(idReserva, dataInicio, dataInicio.plusHours(2), "CANCELADA");
            when(cancelarReserva.execute(reserva.getId(), reserva.getCliente().getEmail())).thenReturn(reserva);
            //Act
            mockMvc.perform(put("/reservas/{idReserva}/cancelar/{idCliente}", idReserva, reserva.getCliente().getEmail())).andExpect(status().isOk());

            //Assert
            verify(cancelarReserva, times(1)).execute(any(Long.class), any(String.class));
        }
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}
