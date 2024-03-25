package com.fase2.techchallenge.fiap.infrastructure.comentario.controller;

import com.fase2.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.comentario.Comentar;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckin;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckout;
import com.fase2.techchallenge.fiap.usecase.reserva.Reservar;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComentarioControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    Reservar reservar;

    @Autowired
    RealizarCheckin realizarCheckin;

    @Autowired
    RealizarCheckout realizarCheckout;

    @Autowired
    Comentar comentar;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class registraComentario {
        @Test
        void devePermitirComentar() throws Exception {

            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "felipe.silveira@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);
            realizarCheckin.execute(reserva.getId(), "felipe.silveira@example.com");
            realizarCheckout.execute(reserva.getId(), "felipe.silveira@example.com");

            ComentarioInsertDTO comentarioInsertDTO =
                    new ComentarioInsertDTO(reserva.getId(),"ÓTIMA SOBREMESA");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(comentarioInsertDTO)
            .when()
                    .post("/api-restaurante/comentarios")
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/comentario/comentario.schema.json"));
        }

        @Test
        void devePermitirAtualizarComentario() throws Exception {

            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "felipe.silveira@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);
            realizarCheckin.execute(reserva.getId(), "felipe.silveira@example.com");
            realizarCheckout.execute(reserva.getId(), "felipe.silveira@example.com");

            ComentarioInsertDTO comentarioInsertDTO =
                    new ComentarioInsertDTO(reserva.getId(),"ÓTIMA SOBREMESA");
            Comentario comentario = comentar.execute(comentarioInsertDTO);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(comentarioInsertDTO)
                    .when()
                    .put("/api-restaurante/comentarios/{id}", comentario.getId())
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/comentario/comentario.schema.json"));
        }

        @Test
        void devePermitirApagarComentario() throws Exception {

            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute());
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "felipe.silveira@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);
            realizarCheckin.execute(reserva.getId(), "felipe.silveira@example.com");
            realizarCheckout.execute(reserva.getId(), "felipe.silveira@example.com");

            ComentarioInsertDTO comentarioInsertDTO =
                    new ComentarioInsertDTO(reserva.getId(),"ÓTIMA SOBREMESA");
            Comentario comentario = comentar.execute(comentarioInsertDTO);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(comentarioInsertDTO)
                    .when()
                    .delete("/api-restaurante/comentarios/{id}", comentario.getId())
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

    }
}
