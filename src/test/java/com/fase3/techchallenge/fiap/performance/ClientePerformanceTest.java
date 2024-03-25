package com.fase3.techchallenge.fiap.performance;

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

public class ClientePerformanceTest extends Simulation {
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .header("Content-Type", "application/json");

    String clienteInsert = "{\"email\":\"joao-wick@email.com\",\"nome\":\"Joao Wick da Silva\",\"situacao\": \"ATIVO\",\"dataNascimento\": \"1990-05-19\",\"endereco\": " +
            "{\"logradouro\": \"Rua Fidencio Ramos\",\"numero\": \"408\",\"bairro\": \"Vila Olimpia\",\"complemento\": \"13 A\",\"cep\": 679116,\"cidade\": \"São Paulo\",\"estado\": \"SP\",\"referencia\": \"Proximo ao Shopping Vila Olimpia\"}";

    String clienteUpdate = "{\"nome\":\"Joao Wick da Silva\",\"situacao\": \"ATIVO\",\"dataNascimento\": \"1990-05-19\",\"endereco\": " +
            "{\"logradouro\": \"Rua Fidencio Ramos\",\"numero\": \"408\",\"bairro\": \"Vila Olimpia\",\"complemento\": \"13 A\",\"cep\": 679116,\"cidade\": \"São Paulo\",\"estado\": \"SP\",\"referencia\": \"Proximo ao Shopping Vila Olimpia\"}";

    ActionBuilder cadastrarClienteRequest = http("cadastrar cliente")
            .post("/api-restaurante/clientes")
            .body(StringBody(clienteInsert))
            .check(status().is(HttpStatus.CREATED.value()));

    ActionBuilder buscarClienteRequest = http("buscar cliente")
            .get("/api-restaurante/clientes/#{id}")
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder atualizarClienteRequest = http("atualizar cliente")
            .put("/api-restaurante/clientes/#{id}")
            .body(StringBody(clienteUpdate))
            .check(status().is(HttpStatus.ACCEPTED.value()));

    ActionBuilder removerClienteRequest = http("remover cliente")
            .delete("/api-restaurante/clientes/#{id}")
            .check(status().is(HttpStatus.OK.value()));

    ScenarioBuilder cenarioCadastrarCliente = scenario("Cadastrar Cliente")
            .exec(cadastrarClienteRequest);

    ScenarioBuilder cenarioBuscarCliente = scenario("Buscar Cliente")
            .exec(buscarClienteRequest);

    ScenarioBuilder cenarioAlterarCliente = scenario("Alterar Cliente")
            .exec(atualizarClienteRequest);

    ScenarioBuilder cenarioRemoverCliente = scenario("Remover Cliente")
            .exec(removerClienteRequest);

    {
        setUp(
                cenarioCadastrarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioBuscarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAlterarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioRemoverCliente.injectOpen(
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
