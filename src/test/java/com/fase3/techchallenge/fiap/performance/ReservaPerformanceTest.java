package com.fase3.techchallenge.fiap.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ReservaPerformanceTest extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080/api-restaurante")
            .header("Content-Type", "application/json");

    ActionBuilder cadastrarReservaRequest = http("Cadastrar Reserva")
            .post("/reservas")
            .body(StringBody(session -> {
                int dayOfMonth = ThreadLocalRandom.current().nextInt(1, 29);
                int hour = ThreadLocalRandom.current().nextInt(0, 24);
                int minute = ThreadLocalRandom.current().nextInt(0, 60);
                LocalDateTime dateTeste = LocalDateTime.of(2024, 3, dayOfMonth, hour, minute);
                return "{\"idRestaurante\":1,\"idMesa\":1,\"idCliente\":\"maria.santos@example.com\",\"dataHoraInicio\":\"" + dateTeste + "\",\"quantidadeHoras\":0}";
            }))
            .asJson()
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.id").saveAs("id"));

    ActionBuilder buscarReservaRequest = http("Buscar Reserva")
            .get("/reservas/#{id}")
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ScenarioBuilder cenarioCadastrarReserva = scenario("Cadastrar Reserva")
            .exec(cadastrarReservaRequest);

    ScenarioBuilder cenarioCadastrarBuscarReserva = scenario("Cadatrar e Buscar Reserva")
            .exec(cadastrarReservaRequest)
            .exec(buscarReservaRequest);

    {
        setUp(
                cenarioCadastrarReserva.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioCadastrarBuscarReserva.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                )

        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(360)
                );
    }
}