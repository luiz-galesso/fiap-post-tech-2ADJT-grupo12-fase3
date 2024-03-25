package com.fase3.techchallenge.fiap.infrastructure.cliente.controller;

import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase3.techchallenge.fiap.usecase.cliente.CadastrarCliente;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class ClienteControllerIT {

    @Autowired
    CadastrarCliente cadastrarCliente;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegistrarCliente {

        @Test
        void devePermitirCadastrarCliente() throws Exception {
            var cliente = ClienteHelper.gerarCliente("jarlan-silva@email.com", "Jarlan Silva", "ATIVO", LocalDate.of(1983, 12, 27));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clienteInsertDTO)
                    .when()
                    .post("/api-restaurante/clientes")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/cliente/cliente_schema.json"));
        }

        @Test
        void deveGerarExcecao_QuandoCadastrarCliente_JaCadastrado() {
            var cliente = ClienteHelper.gerarCliente("eduardo.melo@example.com", "Eduardo Melo", "ATIVO", LocalDate.of(1990, 05, 19));
            ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                    , cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clienteInsertDTO)
                    .when()
                    .post("/api-restaurante/clientes")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    @Nested
    class BuscarCliente {

        @Test
        void devePermitirBuscarCliente() throws Exception {
            var id = "ricardo.santana@example.com";
            when()
                    .get("/api-restaurante/clientes/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveGerarExcecao_QuandoBuscarCliente_NaoLocalizado() throws Exception {
            var id = "joao.wick";
            given().when()
                    .get("/api-restaurante/clientes/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class AlterarCliente {

        @Test
        void devePermitirAlterarCliente() throws Exception {
            var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
            ClienteUpdateDTO clienteUpdateDTO = new ClienteUpdateDTO(cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());

            cadastrarCliente.execute(new ClienteInsertDTO(cliente.getEmail(), cliente.getNome(), cliente.getSituacao(), cliente.getDataNascimento(), cliente.getEndereco()));

            given()
                    //.filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clienteUpdateDTO)
                    .when()
                    .put("/api-restaurante/clientes/{id}", cliente.getEmail())
                    .then()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .body(matchesJsonSchemaInClasspath("schemas/cliente/cliente_schema.json"));
        }

        @Test
        void deveGerarExcecao_QuandoAlterarCliente_ClienteNaoCadastrado() throws Exception {
            var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick da Silva", "ATIVO", LocalDate.of(1990, 05, 19));
            ClienteUpdateDTO clienteUpdateDTO = new ClienteUpdateDTO(cliente.getNome()
                    , cliente.getSituacao()
                    , cliente.getDataNascimento()
                    , cliente.getEndereco());

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(clienteUpdateDTO)
                    .when()
                    .put("/api-restaurante/clientes/{id}", cliente.getEmail())
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    @Nested
    class RemoverCliente {

        @Test
        void devePermitirRemoverCliente() throws Exception {
            var id = "felipe.albuquerque@example.com";
            when()
                    .delete("/api-restaurante/clientes/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveGerarExcecao_QuandoRemoverCliente_IdNaoExiste() throws Exception {
            var id = "joao.wick@email.com";
            given().when()
                    .delete("/api-restaurante/clientes/{id}", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

}
