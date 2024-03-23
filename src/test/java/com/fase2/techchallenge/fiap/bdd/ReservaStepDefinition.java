package com.fase2.techchallenge.fiap.bdd;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ReservaStepDefinition {
    private Response response;

    private Reserva reserva;

    private ReservaInsertDTO reservaInsertDTO;


    private static final String ENDPOINT_API_RESERVA = "http://localhost:8080/api-restaurante/reservas";

    /* ======= BEGIN: REGISTRAR RESERVA ====== */
    @Quando("registrar uma reserva")
    public Reserva registrar_uma_reserva() {
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 0);

        response = given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(reservaInsertDTO)
                        .when()
                        .post(ENDPOINT_API_RESERVA);

        return response.then().extract().as(Reserva.class);
    }
    @Então("a reserva é registrada com sucesso")
    public void a_reserva_é_registrada_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Então("deverá ser apresentada")
    public void deverá_ser_apresentada() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/reserva.schema.json"));
    }
    /* ======= END: REGISTRAR RESERVA ====== */

    /* ======= BEGIN: BUSCAR RESERVA PELO ID ====== */
    @Dado("que a reserva já foi efetuada")
    public void que_a_reserva_já_foi_efetuada() {
        reserva = registrar_uma_reserva();
    }
    @Quando("efetuar a busca da reserva pelo ID")
    public void efetuar_a_busca_da_reserva_pelo_id() {
        response = when()
                .get("/api-restaurante/reservas/{id}", reserva.getId());

    }
    @Então("exibir reserva")
    public void exibir_reserva() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/busca.reserva.schema.json"));
    }
    /* ======= END: BUSCAR RESERVA PELO ID ====== */

    /* ======= BEGIN: BUSCAR POR ID DO RESTAURANTE E DATA ====== */
    @Quando("efetuar a busca por reservas utilizando o ID do restaurante e uma data")
    public void efetuar_a_busca_por_reservas_utilizando_o_id_do_restaurante_e_uma_data() {
        //Arrange
        Long idRestaurante = 1L;
        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        response = when()
                .get("/api-restaurante/reservas/restaurante/{idRestaurante}/data/{data}", idRestaurante, formattedDate);

    }
    @Então("exibir reservas encontradas")
    public void exibir_reservas_encontradas() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
    /* ======= END: BUSCAR POR ID DO RESTAURANTE E DATA ====== */

    /* ======= BEGIN: BUSCAR RESERVA ATIVAS DO CLIENTE ====== */
    @Quando("efetuar a busca das reservas ativas, utilizando o ID do cliente")
    public void efetuar_a_busca_das_reservas_ativas_utilizando_o_id_do_cliente() {
        String idCliente = "maria.santos@example.com";
        response = given()
                .queryParam("page", "0")
                .queryParam("size", "1")
                .when()
                .get("/api-restaurante/reservas/cliente/{idCliente}", idCliente);
    }
    @Então("exibir reservas ativas encontradas")
    public void exibir_reservas_ativas_encontradas() {
        response
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/pageable.busca.reserva.schema.json"));
    }
    /* ======= END: BUSCAR RESERVA ATIVAS DO CLIENTE ====== */

    /* ======= BEGIN: CHECKIN ====== */
    @Dado("que exista uma reserva válida")
    public void que_exista_uma_reserva_válida() {
        LocalDateTime dataInicio = LocalDateTime.now();
        ReservaInsertDTO reservaInsertDTO = new ReservaInsertDTO(1L, 1L, "maria.santos@example.com", dataInicio, 1);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservaInsertDTO)
                .when()
                .post(ENDPOINT_API_RESERVA);

        reserva = response.then().extract().as(Reserva.class);
    }
    @Quando("realizar checkin")
    public void realizar_checkin() {
        response =  when()
                .put("/api-restaurante/reservas/{idReserva}/checkin/{idCliente}", reserva.getId(), reserva.getCliente().getEmail());
    }
    @Então("deverá ser exibido o checkin")
    public void deverá_ser_exibido_o_checkin() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/checkin.reserva.schema.json"));
    }
    /* ======= END: CHECKIN ====== */

    /* ======= BEGIN: CHECKOUT ====== */
    @Quando("realizar checkout")
    public void realizar_checkout() {
        response =  when()
                .put("/api-restaurante/reservas/{idReserva}/checkout/{idCliente}", reserva.getId(), reserva.getCliente().getEmail());
    }

    @Então("deverá ser exibido o checkout")
    public void deverá_ser_exibido_o_checkout() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/checkout.reserva.schema.json"));
    }

    /* ======= END: CHECKOUT ====== */

    /* ======= BEGIN: CANCELAR ====== */
    @Quando("solicitar o cancelamento")
    public void solicitar_o_cancelamento() {
        response = when()
                .put("/api-restaurante/reservas/{idReserva}/cancelar/{idCliente}", reserva.getId(), reserva.getCliente().getEmail());
    }
    @Então("a reserva é cancelada com sucesso")
    public void a_reserva_é_cancelada_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/cancelar.reserva.schema.json"));
    }
    /* ======= END: CANCELAR ====== */

}
