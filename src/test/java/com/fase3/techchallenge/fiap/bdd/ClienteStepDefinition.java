package com.fase3.techchallenge.fiap.bdd;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase3.techchallenge.fiap.utils.ClienteHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class ClienteStepDefinition {
    private Response response;
    private Cliente clienteRetorno;
    private static final String ENDPOINT_API_CLIENTE = "http://localhost:8080/api-restaurante/clientes";

    @Quando("registrar um cliente")
    public Cliente registrarUmCliente() {
        var cliente = ClienteHelper.gerarCliente("joao-wick@email.com", "Joao Wick", "ATIVO", LocalDate.of(1983, 12, 27));
        ClienteInsertDTO clienteInsertDTO = new ClienteInsertDTO(cliente.getEmail()
                , cliente.getNome()
                , cliente.getSituacao()
                , cliente.getDataNascimento()
                , cliente.getEndereco());

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteInsertDTO)
                .when().post(ENDPOINT_API_CLIENTE);
        return response.then().extract().as(Cliente.class);
    }

    @Então("o cliente é registrado com sucesso")
    public void ClienteRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deverá ser apresentado")
    public void deveráSerApresentado() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/cliente/cliente_schema.json"));
    }

    @Dado("que o cliente ja foi cadastrado")
    public void oClienteJaFoiCadastrado() {
        clienteRetorno = registrarUmCliente();
    }

    @Quando("efetuar a busca do cliente pelo email")
    public void efetuarBuscaDoClientePeloEmail() {
        response = when()
                .get(ENDPOINT_API_CLIENTE + "/{id}", clienteRetorno.getEmail());
    }

    @Então("exibir cliente")
    public void exibirCliente() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/cliente/cliente_schema.json"));
    }

    @Quando("efetuar a requisicao para alterar o cliente")
    public void efetuarRequisicaoParaAlterarCliente() {
        clienteRetorno.setNome("Joao Wick II");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRetorno)
                .when()
                .put(ENDPOINT_API_CLIENTE + "/{id}", clienteRetorno.getEmail());
    }

    @Então("o cliente é atualizado com sucesso")
    public void clienteAtualizaComSucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("efetuar a requisicao para remover o cliente")
    public void efetuarRequisicaoParaRemoverCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(ENDPOINT_API_CLIENTE + "{id}", clienteRetorno.getEmail());
    }

    @Então("o cliente é removida com sucesso")
    public void clienteRemovidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("Cliente Removido"));
    }

}
