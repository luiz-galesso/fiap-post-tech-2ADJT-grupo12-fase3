package com.fase2.techchallenge.fiap.infrastructure.comentario.controller;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.comentario.controller.dto.ComentarioInsertDTO;
import com.fase2.techchallenge.fiap.usecase.reserva.ObterReservaPeloId;
import com.fase2.techchallenge.fiap.usecase.reserva.Reservar;
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
    /*private final Reservar reservar;

    private final ObterReservaPeloId obterReservaPeloId;

    public ComentarioController(Reservar reservar, ObterReservaPeloId obterReservaPeloId) {
        this.reservar = reservar;
        this.obterReservaPeloId = obterReservaPeloId;
    }

    @Operation( summary= "Realiza uma nova reserva", description= "Serviço utilizado para realizar uma novo reversa.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ComentarioInsertDTO reservaInsertDTO) {
        Reserva reserva = reservar.execute(reservaInsertDTO);
        return new ResponseEntity<>(reserva, HttpStatus.CREATED);
    }

    @Operation( summary= "Busca uma reserva pelo Id", description= "Serviço utilizado para buscar uma reserva pelo Id.")
    @GetMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Reserva reserva  = obterReservaPeloId.execute(id);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }*/

}
