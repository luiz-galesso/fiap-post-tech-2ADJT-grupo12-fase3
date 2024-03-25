package com.fase2.techchallenge.fiap.infrastructure.comentario.controller;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.comentario.utils.ComentarioHelper;
import com.fase2.techchallenge.fiap.infrastructure.reserva.utils.ReservaHelper;
import com.fase2.techchallenge.fiap.usecase.comentario.ApagarComentario;
import com.fase2.techchallenge.fiap.usecase.comentario.Comentar;
import com.fase2.techchallenge.fiap.usecase.comentario.EditarComentario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static com.fase2.techchallenge.fiap.infrastructure.reserva.controller.ReservaControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ComentarioControllerTest {

    @Mock
    Comentar comentar;

    @Mock
    EditarComentario editarComentario;

    @Mock
    ApagarComentario apagarComentario;
    AutoCloseable mock;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ComentarioController comentarioController = new ComentarioController(comentar, editarComentario, apagarComentario);
        mockMvc = MockMvcBuilders.standaloneSetup(comentarioController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RegistrarComentario {
        @Test
        void devePermitirRegistrarComentario() throws Exception {

            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());
            Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(3), "RESERVADO");
            Comentario comentario = ComentarioHelper.criarComentario();
            ComentarioInsertDTO comentarioInsertDTO = new ComentarioInsertDTO(reserva.getId(), "COMIDA RUIM");
            when(comentar.execute(any(ComentarioInsertDTO.class))).thenReturn(comentario);

            //Act
            mockMvc.perform(post("/comentarios").content(asJsonString(comentarioInsertDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

            //Assert
            verify(comentar, times(1)).execute(any(ComentarioInsertDTO.class));

        }

        @Test
        void devePermitirEditarComentario() throws Exception {
            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());
            Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(3), "RESERVADO");
            Comentario comentario = ComentarioHelper.criarComentario();
            ComentarioInsertDTO comentarioInsertDTO = new ComentarioInsertDTO(reserva.getId(), "COMIDA RUIM");
            when(editarComentario.execute(any(Long.class), any(String.class))).thenReturn(comentario);

            //Act
            mockMvc.perform(put("/comentarios/{id}", reserva.getId()).content("COMIDA MEDIANA").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

            //Assert
            verify(editarComentario, times(1)).execute(any(Long.class), any(String.class));

        }

        @Test
        void devePermitirApagarComentario() throws Exception {
            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());
            Reserva reserva = ReservaHelper.gerarReserva(1L, dataInicio, dataInicio.plusHours(3), "RESERVADO");
            Comentario comentario = ComentarioHelper.criarComentario();
            ComentarioInsertDTO comentarioInsertDTO = new ComentarioInsertDTO(reserva.getId(), "COMIDA RUIM");

            //Act
            mockMvc.perform(delete("/comentarios/{id}", comentario.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

            //Assert
            verify(apagarComentario, times(1)).execute(any(Long.class));

        }
    }
}
