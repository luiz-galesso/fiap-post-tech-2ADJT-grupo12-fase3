package com.fase2.techchallenge.fiap.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.HttpStatus;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ComentarioPerformanceTest extends Simulation {
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .header("Content-Type", "application/json");

    String cadastroCmentarioBody = "{\"idReserva\":5,\"texto\":\"Comentário\"}";
    ActionBuilder cadastrarComentarioRequest = http("cadastrar comentario")
            .post("/api-restaurante/comentario")
            .body(StringBody(cadastroCmentarioBody))
            .check(status().is(HttpStatus.CREATED.value()));

    String editarCmentarioBody = "Comidamuitosalgada";
    ActionBuilder editarComentarioRequest = http("editar comentario")
            .put("/api-restaurante/comentario/#{id}")
            .body(StringBody(editarCmentarioBody))
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder apagarComentarioRequest = http("apagar comentario")
            .delete("/api-restaurante/comentario/#{id}")
            .check(status().is(HttpStatus.NO_CONTENT.value()));

    ScenarioBuilder cenarioCadastrarComentarioRequest = scenario("Cadastrar Comentario")
            .exec(cadastrarComentarioRequest);

    ScenarioBuilder cenarioEditarComentarioRequest = scenario("Editar Comentario")
            .exec(editarComentarioRequest);

    ScenarioBuilder cenarioApagarComentarioRequest = scenario("Apagar Comentario")
            .exec(apagarComentarioRequest);

    {
        setUp(
                cenarioCadastrarComentarioRequest.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),

                cenarioEditarComentarioRequest.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))),

                cenarioApagarComentarioRequest.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))))

                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(50),
                        global().failedRequests().count().is(0L));
    }
}
