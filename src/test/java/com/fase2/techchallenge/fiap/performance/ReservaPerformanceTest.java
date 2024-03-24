package com.fase2.techchallenge.fiap.performance;

import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.Body;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ReservaPerformanceTest extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
            .header("Content-Type", "application/json");

    LocalDateTime dataInicio = LocalDateTime.now();
    ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 2);

    String reservaBody = "{\"idRestaurante\":1,\"idMesa\":1,\"idCliente\":\"maria.santos@example.com\",\"dataHoraInicio\":\"2024-03-24T00:00:00Z\",\"quantidadeHoras\":2}";

    ActionBuilder reservarMesaRequest = http("Reservar mesa")
            .post("/api-restaurante/reservas")
            .body(StringBody(reservaBody))
            .check(status().is(HttpStatus.CREATED.value()));

    ScenarioBuilder cenarioReservaMesa = scenario("reservar mesa")
            .exec(reservarMesaRequest);

    {
        setUp(
                cenarioReservaMesa.injectOpen(
                        rampUsersPerSec(1).to(1).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(1).during(Duration.ofSeconds(10)),
                        rampUsersPerSec(1).to(1).during(Duration.ofSeconds(10))
                )

        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(50)
                );
    }
}
