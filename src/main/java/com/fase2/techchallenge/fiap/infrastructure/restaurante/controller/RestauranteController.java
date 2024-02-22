package com.fase2.techchallenge.fiap.infrastructure.restaurante.controller;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase2.techchallenge.fiap.usecase.restaurante.CriarRestaurante;
import com.fase2.techchallenge.fiap.usecase.restaurante.ObterRestaurantePeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description="Serviços para manipular restaurantes")
public class RestauranteController {
    private final CriarRestaurante criarRestaurante;

    private final ObterRestaurantePeloId obterRestaurantePeloId;

    public RestauranteController(CriarRestaurante criarRestaurante, ObterRestaurantePeloId obterRestaurantePeloId) {
        this.criarRestaurante = criarRestaurante;
        this.obterRestaurantePeloId = obterRestaurantePeloId;
    }

    @Operation( summary= "Cadastra um novo restaurante", description= "Serviço utilizado para cadastrar um novo restaurante.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody RestauranteInsertDTO restauranteInsertDTO) {
        Restaurante restaurante = criarRestaurante.execute(restauranteInsertDTO);
        return new ResponseEntity<>(restaurante, HttpStatus.CREATED);
    }

    @Operation( summary= "Busca um restaurante pelo Id", description= "Serviço utilizado para buscar um restaurante pelo Id.")
    @GetMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Restaurante restaurante = obterRestaurantePeloId.execute(id);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

}
