package com.fase2.techchallenge.fiap.infrastructure.reserva.controller;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.reserva.RealizarCheckin;
import com.fase2.techchallenge.fiap.usecase.reserva.Reservar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ReservaControllerIT {

    @Autowired
    Reservar reservar;

    @Autowired
    RealizarCheckin realizarCheckin;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegistrarReserva {
        @Test
        void devePermitirRegistrarReserva() throws Exception {

            LocalDateTime dataInicio = LocalDateTime.now();
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);

            given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservaInsertDTO)
            .when()
                .post("/api-restaurante/reservas")
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/reserva.schema.json"));
        }
    }

    @Nested
    class BuscarReserva {
        @Test
        void devePermitirBuscarReservaPeloId() throws Exception {

            //Arrange
            LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);

            when()
                    .get("/api-restaurante/reservas/{id}", reserva.getId())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/busca.reserva.schema.json"));
        }

        @Test
        void devePermitirBuscarReservaPeloRestauranteEData() throws Exception {
            //Arrange
            Long idRestaurante = 1L;
            LocalDate localDate = LocalDate.now();
            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            when()
                    .get("/api-restaurante/reservas/restaurante/{idRestaurante}/data/{data}", idRestaurante, formattedDate)
            .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void devePermitirBuscarReservasAtivasPorCliente() throws Exception {
            String idCliente = "maria.santos@example.com";
            given()
                    .queryParam("page", "0")
                    .queryParam("size", "1")
            .when()
                    .get("/api-restaurante/reservas/cliente/{idCliente}", idCliente)
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/pageable.busca.reserva.schema.json"));
        }
    }

    @Nested
    class AtualizarReserva {
        @Test
        void deveRealizarCheckin() throws Exception {

            //Arrange
            LocalDateTime dataInicio = LocalDateTime.now();
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);

            when()
                    .put("/api-restaurante/reservas/{idReserva}/checkin/{idCliente}", reserva.getId(), reserva.getCliente().getEmail())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/checkin.reserva.schema.json"));
        }

        @Test
        void deveRealizarCheckout() throws Exception {
            //Arrange
            LocalDateTime dataInicio = LocalDateTime.now();
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);
            reserva = realizarCheckin.execute(reserva.getId(), reserva.getCliente().getEmail());

            when()
                    .put("/api-restaurante/reservas/{idReserva}/checkout/{idCliente}", reserva.getId(), reserva.getCliente().getEmail())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/checkout.reserva.schema.json"));
        }

        @Test
        void devePermitirCancelarReserva() throws Exception {
            //Arrange
            LocalDateTime dataInicio = LocalDateTime.now();
            ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);
            Reserva reserva = reservar.execute(reservaInsertDTO);

            when()
                    .put("/api-restaurante/reservas/{idReserva}/cancelar/{idCliente}", reserva.getId(), reserva.getCliente().getEmail())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/cancelar.reserva.schema.json"));
        }
    }
}
