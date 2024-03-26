package com.fase3.techchallenge.fiap.infrastructure.comentario.controller;

import com.fase3.techchallenge.fiap.entity.comentario.model.Comentario;
import com.fase3.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase3.techchallenge.fiap.usecase.comentario.ApagarComentario;
import com.fase3.techchallenge.fiap.usecase.comentario.Comentar;
import com.fase3.techchallenge.fiap.usecase.comentario.EditarComentario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentarios", description="Serviços para manipular comentários das reservas realizadas")
public class ComentarioController {

    private final Comentar comentar;

    private final EditarComentario editarComentario;

    private final ApagarComentario apagarComentario;

    public ComentarioController(Comentar comentar, EditarComentario editarComentario, ApagarComentario apagarComentario) {
        this.comentar = comentar;
        this.editarComentario = editarComentario;
        this.apagarComentario = apagarComentario;
    }

    @Operation( summary= "Realiza um comentário de uma reserva", description= "Serviço utilizado para realizar um comentário de uma reversa.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ComentarioInsertDTO comentarioInsertDTO) {
        Comentario comentario = comentar.execute(comentarioInsertDTO);
        return new ResponseEntity<>(comentario, HttpStatus.CREATED);
    }

    @Operation( summary= "Edita o comentário de uma reserva", description= "Serviço utilizado para editar o comentário de uma reserva.")
    @PutMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String texto) {
        Comentario comentario  = editarComentario.execute(id, texto);
        return new ResponseEntity<>(comentario, HttpStatus.OK);
    }

    @Operation( summary= "Apaga o comentário de uma reserva", description= "Serviço utilizado para apagar o comentário de uma reserva.")
    @DeleteMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        apagarComentario.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
