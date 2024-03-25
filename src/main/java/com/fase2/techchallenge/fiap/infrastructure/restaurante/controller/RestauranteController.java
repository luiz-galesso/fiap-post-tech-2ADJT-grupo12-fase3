package com.fase2.techchallenge.fiap.infrastructure.restaurante.controller;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteInsertDTO;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto.RestauranteUpdateDTO;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import com.fase2.techchallenge.fiap.usecase.restaurante.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Serviços para manipular restaurantes")
public class RestauranteController {
    private final CriarRestaurante criarRestaurante;

    private final ObterRestaurantePeloId obterRestaurantePeloId;

    private final BuscarRestaurantePeloNome buscarRestaurantePeloNome;

    private final BuscarRestaurantePeloTipo buscarRestaurantePeloTipo;

    private final BuscarRestaurantePelaLocalizacao buscarRestaurantePelaLocalizacao;

    private final AtualizarRestaurante atualizarRestaurante;

    public RestauranteController(
            CriarRestaurante criarRestaurante,
            ObterRestaurantePeloId obterRestaurantePeloId,
            BuscarRestaurantePeloNome buscarRestaurantePeloNome,
            BuscarRestaurantePeloTipo buscarRestaurantePeloTipo,
            BuscarRestaurantePelaLocalizacao buscarRestaurantePelaLocalizacao,
            AtualizarRestaurante atualizarRestaurante) {
        this.criarRestaurante = criarRestaurante;
        this.obterRestaurantePeloId = obterRestaurantePeloId;
        this.buscarRestaurantePeloNome = buscarRestaurantePeloNome;
        this.buscarRestaurantePeloTipo = buscarRestaurantePeloTipo;
        this.buscarRestaurantePelaLocalizacao = buscarRestaurantePelaLocalizacao;
        this.atualizarRestaurante = atualizarRestaurante;
    }

    @Operation(summary = "Cadastra um novo restaurante", description = "Serviço utilizado para cadastrar um novo restaurante.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody RestauranteInsertDTO restauranteInsertDTO) {
        Restaurante restaurante = criarRestaurante.execute(restauranteInsertDTO);
        return new ResponseEntity<>(restaurante, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um restaurante pelo Id", description = "Serviço utilizado para buscar um restaurante pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Restaurante restaurante = obterRestaurantePeloId.execute(id);
            return new ResponseEntity<>(restaurante, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Busca restaurante por nome", description = "Serviço utilizado para buscar um restaurante pelo nome.")
    @GetMapping("/busca-nome/{nome}")
    public ResponseEntity<?> findByNomeContaining(@PathVariable String nome) {
        if (nome.length() < 3) return new ResponseEntity<>("Digite pelo menos 3 letras.", HttpStatus.BAD_REQUEST);

        List<Restaurante> restaurantes = this.buscarRestaurantePeloNome.execute(nome);

        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @Operation(summary = "Busca restaurante por tipo da culinaria", description = "Serviço utilizado para buscar um restaurante pelo tipo da culinaria.")
    @GetMapping("/busca-tipo-culinaria/{tipo}")
    public ResponseEntity<?> findByTipoCulinariaContaining(@PathVariable String tipo) {
        if (tipo.length() < 3) return new ResponseEntity<>("Digite pelo menos 3 letras.", HttpStatus.BAD_REQUEST);

        List<Restaurante> restaurantes = this.buscarRestaurantePeloTipo.execute(tipo);

        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @Operation(summary = "Busca restaurante por endereço", description = "Serviço utilizado para buscar um restaurante por endereço.")
    @GetMapping("/busca-endereco")
    public ResponseEntity<?> findByEndereco(
            @RequestParam(required = false) String logradouro,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long cep) {

        List<Restaurante> restaurantes = this.buscarRestaurantePelaLocalizacao.execute(new Endereco(logradouro, numero, bairro, cep, cidade, estado));

        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza cadastro do restaurante", description = "Serviço utilizado para atualizar cadastro do restaurante")
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> update(@PathVariable Long id, @RequestBody RestauranteUpdateDTO restauranteUpdateDTO) {
        Restaurante restaurante = atualizarRestaurante.execute(id, restauranteUpdateDTO);

        if (restaurante == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(restaurante);
    }

}
