package com.fase3.techchallenge.fiap.infrastructure.avaliacao.controller;

import com.fase3.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase3.techchallenge.fiap.infrastructure.avaliacao.controller.dto.AvaliacaoInsertDTO;
import com.fase3.techchallenge.fiap.usecase.avaliacao.ApagarAvaliacao;
import com.fase3.techchallenge.fiap.usecase.avaliacao.EditarAvaliacao;
import com.fase3.techchallenge.fiap.usecase.avaliacao.Avaliar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description="Serviços para manipular as avaliações de uma reserva.")
public class AvaliacaoController {

    private final Avaliar avaliar;

    private final EditarAvaliacao editarAvaliacao;

    private final ApagarAvaliacao apagarAvaliacao;

    public AvaliacaoController(Avaliar avaliar, EditarAvaliacao editarAvaliacao, ApagarAvaliacao apagarAvaliacao) {
        this.avaliar = avaliar;
        this.editarAvaliacao = editarAvaliacao;
        this.apagarAvaliacao = apagarAvaliacao;
    }

    @Operation( summary= "Realiza uma nova avaliação de uma reserva", description= "Serviço utilizado para realizar uma nova avaliação de uma reserva.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody AvaliacaoInsertDTO avaliacaoInsertDTO) {
        Avaliacao avaliacao = avaliar.execute(avaliacaoInsertDTO);
        return new ResponseEntity<>(avaliacao, HttpStatus.CREATED);
    }

    @Operation( summary= "Edita a avaliação de uma reserva", description= "Serviço utilizado para editar a avaliação de uma reserva.")
    @PutMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Integer valor) {
        Avaliacao avaliacao  = editarAvaliacao.execute(id, valor);
        return new ResponseEntity<>(avaliacao, HttpStatus.OK);
    }

    @Operation( summary= "Apaga a avaliação de uma reserva", description= "Serviço utilizado para apagar a avaliação de uma reserva.")
    @DeleteMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        apagarAvaliacao.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
