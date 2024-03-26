package com.fase3.techchallenge.fiap.infrastructure.mesa.controller;

import com.fase3.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase3.techchallenge.fiap.infrastructure.mesa.controller.dto.MesaInsertDTO;
import com.fase3.techchallenge.fiap.usecase.mesa.CriarMesa;
import com.fase3.techchallenge.fiap.usecase.mesa.ObterMesaPeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mesas")
@Tag(name = "Mesas", description="Serviços para manipular mesas")
public class MesaController {
    private final CriarMesa criarMesa;

    private final ObterMesaPeloId obterMesaPeloId;

    public MesaController(CriarMesa criarMesa, ObterMesaPeloId obterMesaPeloId) {
        this.criarMesa = criarMesa;
        this.obterMesaPeloId = obterMesaPeloId;
    }

    @Operation( summary= "Cadastra uma nova mesa", description= "Serviço utilizado para cadastrar uma nova mesa.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody MesaInsertDTO restauranteInsertDTO) {
        Mesa mesa = criarMesa.execute(restauranteInsertDTO);
        return new ResponseEntity<>(mesa, HttpStatus.CREATED);
    }

    @Operation( summary= "Busca uma mesa pelo Id", description= "Serviço utilizado para buscar uma mesa pelo Id.")
    @GetMapping(value="/{idRestaurante}/{idMesa}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long idRestaurante, @PathVariable Long idMesa) {
        Mesa mesa = obterMesaPeloId.execute(idRestaurante, idMesa);
        return new ResponseEntity<>(mesa, HttpStatus.OK);
    }

}
