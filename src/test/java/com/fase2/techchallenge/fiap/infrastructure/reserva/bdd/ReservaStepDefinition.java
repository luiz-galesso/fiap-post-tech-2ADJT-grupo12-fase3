package com.fase2.techchallenge.fiap.infrastructure.reserva.bdd;

import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Dados;
import io.cucumber.java.pt.Então;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import static io.restassured.RestAssured.given;

public class ReservaStepDefinition {
    private Response response;

    private static final String ENDPOINT_API_RESERVA = "http://localhost:8080/api-restaurante/reservas";

    @Dado("que tenha a solicitação de reserva, com usuário válido e disponibilidade de reserva")
    public void que_tenha_a_solicitação_de_reserva() {
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservaInsertDTO)
        .when()
                .post(ENDPOINT_API_RESERVA);
    }
    @Então("a reserva é registrada com sucesso")
    public void a_reserva_é_registrada_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());

    }
    @Então("deverá ser apresentada")
    public void deverá_ser_apresentada() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/reserva.schema.json"));
    }

}
