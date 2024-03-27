package com.fase3.techchallenge.fiap.performance;

import com.github.javafaker.Faker;
import io.gatling.javaapi.core.ActionBuilder;
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

    ActionBuilder cadastrarClienteRequest = http("cadastrar cliente")
            .post("/clientes")
            .body(StringBody(session -> {
                int index = session.getString("email").indexOf('@');
                var nome = session.getString("email").substring(0, index).replace(".", " ");
                return "{\"email\":\"" + session.getString("email") + "\",\"nome\":\"" + nome + "\",\"situacao\":\"ATIVO\",\"dataNascimento\":\"1983-12-27\",\"endereco\":{\"logradouro\":\"RuaFidencioRamos\",\"numero\":\"408\",\"bairro\":\"VilaOlimpia\",\"complemento\":\"13A\",\"cep\":679116,\"cidade\":\"SãoPaulo\",\"estado\":\"SP\",\"referencia\":\"ProximoaoShoppingVilaOlimpia\"}}";
            }))
            .asJson()
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.email").saveAs("id"));

    ActionBuilder buscarClienteRequest = http("buscar cliente")
            .get("/clientes/#{id}")
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            }).then()
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder removerClienteRequest = http("remover cliente")
            .delete("/clientes/#{id}")
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder alterarClienteRequest = http("alterar cliente")
            .put("/clientes/#{id}")
            .body(StringBody(session -> {
                int index = session.getString("email").indexOf('@');
                var nome = session.getString("email").substring(0, index).replace(".", " ");
                return "{\"nome\":\"" + nome + "\",\"situacao\":\"INATIVO\",\"dataNascimento\":\"1991-01-13\",\"endereco\":{\"logradouro\":\"RuaFidencioRamos\",\"numero\":\"408\",\"bairro\":\"VilaOlimpia\",\"complemento\":\"13A\",\"cep\":679116,\"cidade\":\"SãoPaulo\",\"estado\":\"SP\",\"referencia\":\"ProximoaoShoppingVilaOlimpia\"}}";
            }))
            .asJson()
            .check(status().is(HttpStatus.ACCEPTED.value()));

    ScenarioBuilder cenarioCadastrarCliente = scenario("Cadastrar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest);

    ScenarioBuilder cenarioAdicionarBuscarCliente = scenario("Adicionar e Buscar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(buscarClienteRequest);

    ScenarioBuilder cenarioAdicionarRemoverCliente = scenario("Adicionar e Remover Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(removerClienteRequest);

    ScenarioBuilder cenarioAdicionarAlterarCliente = scenario("Adicionar e Alterar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(alterarClienteRequest);

    {
        setUp(
                cenarioCadastrarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarBuscarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarRemoverCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarAlterarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10)))

        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(900)
                );
    }

}
