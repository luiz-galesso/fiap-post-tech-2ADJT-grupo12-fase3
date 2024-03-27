package com.fase3.techchallenge.fiap.bdd;

import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteUpdateDTO;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class RestauranteStepDefinition {
    private static final String ENDPOINT_API_RESTAURANTE = "http://localhost:8080/api-restaurante/restaurantes";
    private Response response;
    private Restaurante restaurante;
    private RestauranteInsertDTO restauranteInsertDTO;
    private RestauranteUpdateDTO restauranteUpdateDTO;

    @Quando("cadastrar um restaurante")
    public Restaurante cadastrar_um_restaurante() {
        var restaurante = RestauranteHelper.gerarRestaurante(null);
        var restauranteInsertDTO = new RestauranteInsertDTO(restaurante.getNome(),
                restaurante.getCnpj(),
                restaurante.getEndereco(),
                restaurante.getTipoCulinaria(),
                restaurante.getCapacidade(),
                restaurante.getSituacao(),
                restaurante.getHorarioFuncionamento());
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteInsertDTO)
                .when()
                .post(ENDPOINT_API_RESTAURANTE);
        return response.then().extract().as(Restaurante.class);
    }

    @Então("o restaurante é cadastrado com sucesso")
    public void o_restaurante_é_cadastrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Dado("que o restaurante foi cadastrado")
    public void que_o_restaurante_foi_cadastrado() {
        restaurante = cadastrar_um_restaurante();
    }

    @Quando("efetuar a busca do restaurante pelo identificador")
    public void efetuar_a_busca_do_restaurante_pelo_identificado() {
        response = when()
                .get("/api-restaurante/restaurantes/{id}", restaurante.getId());
    }

    @Então("exibir o restaurante")
    public void exibir_o_restaurante() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/restaurante/RestauranteResponseSchema.json"));
    }

    @Quando("solicitado um restaurante com identificador inválido")
    public void solicitado_um_restaurante_com_identificador_inválido() {
        response = when()
                .get("/api-restaurante/restaurantes/{id}", -1L);
    }

    @Então("exibir um erro de restaurante não localizado")
    public void exibir_um_erro_de_restaurante_não_localizado() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Restaurante não localizado"));
    }

    @Quando("buscar restaurantes pelo nome")
    public void buscar_restaurantes_pelo_nome() {
        response = when()
                .get("/api-restaurante/restaurantes/busca-nome/{nome}", "Parrilla");
    }

    @Então("exibir restaurantes que contenham o nome")
    public void exibir_restaurantes_que_contenham_o_nome() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/restaurante/RestaurantesResponseSchema.json"));
    }

    @Quando("buscar restaurantes pelo tipo de culinária")
    public void buscar_restaurantes_pelo_tipo_de_culinária() {
        response = when()
                .get("api-restaurante/restaurantes/busca-tipo-culinaria/{tipo}", "Argentina");
    }

    @Então("exibir restaurantes que são do tipo de culinária buscado")
    public void exibir_restaurantes_que_são_do_tipo_de_culinária_buscado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/restaurante/RestaurantesResponseSchema.json"));
    }

    @Quando("buscar restaurantes pela localização")
    public void buscar_restaurantes_pela_localização() {
        response = given()
                .queryParam("cidade", "Porto Alegre")
                .queryParam("estado", "RS")
                .when()
                .get("api-restaurante/restaurantes/busca-endereco");
    }

    @Então("exibir restaurantes de uma localização")
    public void exibir_restaurantes_de_uma_localização() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/restaurante/RestaurantesResponseSchema.json"));
    }

    @Dado("que exista um restaurante cadastrado")
    public void que_exista_um_restaurante_cadastrado() {
        restaurante = cadastrar_um_restaurante();
    }

    @Quando("realizar uma alteração no cadastro do restaurante")
    public void realizar_uma_alteração_no_cadastro_do_restaurante() {
        var restauranteUpdateDTO = new RestauranteUpdateDTO();
        restauranteUpdateDTO.setSituacao("INATIVO");

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteUpdateDTO)
                .when()
                .put("/api-restaurante/restaurantes/{id}", restaurante.getId());
    }

    @Então("deverá exibir o cadastro do restaurante atualizado")
    public void deverá_exibir_o_cadastro_do_restaurante_atualizado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/restaurante/RestauranteResponseSchema.json"));
    }


}
