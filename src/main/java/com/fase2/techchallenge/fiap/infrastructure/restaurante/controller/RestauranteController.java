package com.fase2.techchallenge.fiap.infrastructure.restaurante.controller;

import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase2.techchallenge.fiap.usecase.restaurante.BuscarRestaurantePeloNome;
import com.fase2.techchallenge.fiap.usecase.restaurante.BuscarRestaurantePeloTipo;
import com.fase2.techchallenge.fiap.usecase.restaurante.CriarRestaurante;
import com.fase2.techchallenge.fiap.usecase.restaurante.ObterRestaurantePeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description="Serviços para manipular restaurantes")
public class RestauranteController {
    private final CriarRestaurante criarRestaurante;

    private final ObterRestaurantePeloId obterRestaurantePeloId;

    private final BuscarRestaurantePeloNome buscarRestaurantePeloNome;

    private  final BuscarRestaurantePeloTipo buscarRestaurantePeloTipo;

    public RestauranteController(
            CriarRestaurante criarRestaurante,
            ObterRestaurantePeloId obterRestaurantePeloId,
            BuscarRestaurantePeloNome buscarRestaurantePeloNome,
            BuscarRestaurantePeloTipo buscarRestaurantePeloTipo) {
        this.criarRestaurante = criarRestaurante;
        this.obterRestaurantePeloId = obterRestaurantePeloId;
        this.buscarRestaurantePeloNome = buscarRestaurantePeloNome;
        this.buscarRestaurantePeloTipo = buscarRestaurantePeloTipo;
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

    @Operation(summary = "Busca restaurante por nome", description = "Serviço utilizado para buscar um restaurante pelo nome.")
    @GetMapping("/busca-nome")
    public ResponseEntity<?> findByNomeContaining(@RequestParam String nome)
    {

        if(nome.length() < 3) return new ResponseEntity<>("Digite pelo menos 3 letras.", HttpStatus.BAD_REQUEST);

        List<Restaurante> restaurantes = this.buscarRestaurantePeloNome.execute(nome);

        if(restaurantes.isEmpty())
            return new ResponseEntity<>("Nenhum resultado foi encontrado para a busca: " + nome, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @Operation(summary = "Busca restaurante por tipo da culinaria", description = "Serviço utilizado para buscar um restaurante pelo tipo da culinaria.")
    @GetMapping("/busca-tipo-culinaria")
    public ResponseEntity<?> findByTipoCulinariaContaining(@RequestParam String tipo)
    {

        if(tipo.length() < 3) return new ResponseEntity<>("Digite pelo menos 3 letras.", HttpStatus.BAD_REQUEST);

        List<Restaurante> restaurantes = this.buscarRestaurantePeloTipo.execute(tipo);

        if(restaurantes.isEmpty())
            return new ResponseEntity<>("Nenhum resultado foi encontrado para a busca: " + tipo, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

}
