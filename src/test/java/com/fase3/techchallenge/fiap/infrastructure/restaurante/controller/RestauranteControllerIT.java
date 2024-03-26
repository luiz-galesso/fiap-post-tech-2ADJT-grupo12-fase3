package com.fase3.techchallenge.fiap.infrastructure.restaurante.controller;

import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteUpdateDTO;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class RestauranteControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class CriacaoRestaurante {
        @Test
        void devePermitirCriarRestaurante() {
            var restaurante = RestauranteHelper.gerarRestaurante(null);
            var restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome(),
                    restaurante.getCnpj(),
                    restaurante.getEndereco(),
                    restaurante.getTipoCulinaria(),
                    restaurante.getCapacidade(),
                    restaurante.getSituacao(),
                    restaurante.getHorarioFuncionamento());

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(restauranteInsertDTO)
                    .when()
                    .post("/api-restaurante/restaurantes")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestauranteResponseSchema.json"));
        }
    }

    @Nested
    class BuscarRestaurante {
        @Test
        void devePermitirBuscarRestaurantePeloId() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/api-restaurante/restaurantes/{id}", 4L)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestauranteResponseSchema.json"));
        }

        @Test
        void deveGerarExcecaoAoBuscarRestaurantePorIdQueNaoExiste() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/api-restaurante/restaurantes/{id}", -1L)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo("Restaurante não localizado"));
        }

        @Test
        void devePermitirListarRestaurantePeloNome() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/api-restaurante/restaurantes/busca-nome/{nome}", "Parrilla")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestaurantesResponseSchema.json"));
        }

        @Test
        void devePermitirListarRestaurantePeloTipo() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("api-restaurante/restaurantes/busca-tipo-culinaria/{tipo}", "Argentina")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestaurantesResponseSchema.json"));
        }

        @Test
        void devePermitirListarRestaurantePeloEndereco() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .queryParam("cidade", "Porto Alegre")
                    .queryParam("estado", "RS")
                    .when()
                    .get("api-restaurante/restaurantes/busca-endereco")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestaurantesResponseSchema.json"));
        }

    }

    @Nested
    class AlterarRestaurante {
        @Test
        void devePermitirAlterarRestaurante() {
            var restauranteUpdateDTO = new RestauranteUpdateDTO();
            restauranteUpdateDTO.setSituacao("INATIVO");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(restauranteUpdateDTO)
                    .when()
                    .put("/api-restaurante/restaurantes/{id}", 4L)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestauranteResponseSchema.json"));
        }

        @Test
        void devePermitirAlterarRestaurante2() {


            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(any(RestauranteUpdateDTO.class))
                    .when()
                    .put("/api-restaurante/restaurantes/{id}", 999L)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }
}

