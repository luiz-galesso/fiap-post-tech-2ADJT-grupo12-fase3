package com.fase2.techchallenge.fiap.performance;

import com.github.javafaker.Faker;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.HttpStatus;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ClientePerformanceTest extends Simulation {
    private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080/api-restaurante")
            .header("Content-Type", "application/json");
    private final Faker faker = new Faker();
    ScenarioBuilder cenarioCadastrarCliente = scenario("Cadastrar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(http("cadastrar cliente")
                    .post("/clientes")
                    .body(StringBody(session -> {
                        return "{\"email\":\"" + session.getString("email") + "\",\"nome\":\"JarlanSilva\",\"situacao\":\"ATIVO\",\"dataNascimento\":\"1983-12-27\",\"endereco\":{\"logradouro\":\"RuaFidencioRamos\",\"numero\":\"408\",\"bairro\":\"VilaOlimpia\",\"complemento\":\"13A\",\"cep\":679116,\"cidade\":\"SãoPaulo\",\"estado\":\"SP\",\"referencia\":\"ProximoaoShoppingVilaOlimpia\"}}";
                    }))
                    .asJson()
                    .check(status().is(HttpStatus.CREATED.value()))
                    .check(jsonPath("$.email").saveAs("id")))
            .exec(http("buscar cliente")
                    .get("/clientes/#{id}")
                    .check(status().is(HttpStatus.OK.value()))
            );

    {
        setUp(
                cenarioCadastrarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(1)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(1)
                                .during(Duration.ofSeconds(1)),
                        rampUsersPerSec(1)
                                .to(1)
                                .during(Duration.ofSeconds(1))
                )

        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(50)
                );
    }

}
