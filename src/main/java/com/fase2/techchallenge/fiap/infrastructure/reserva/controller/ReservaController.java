package com.fase2.techchallenge.fiap.infrastructure.reserva.controller;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaCheckinoutDTO;
import com.fase2.techchallenge.fiap.infrastructure.reserva.controller.dto.ReservaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.reserva.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description="Serviços para manipular reservas")
@AllArgsConstructor
public class ReservaController {
    private final Reservar reservar;

    private final ObterReservaPeloId obterReservaPeloId;

    private final BuscarReservaPorRestauranteEData buscarReservaPorRestauranteEData;

    private final RealizarCheckin realizarCheckin;

    private final RealizarCheckout realizarCheckout;

    private final CancelarReserva cancelarReserva;

    private final BuscarReservasAtivasPorCliente buscarReservasAtivasPorCliente;

    @Operation( summary= "Realiza uma nova reserva", description= "Serviço utilizado para realizar uma novo reversa.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ReservaInsertDTO reservaInsertDTO) {
        Reserva reserva = reservar.execute(reservaInsertDTO);
        return new ResponseEntity<>(reserva, HttpStatus.CREATED);
    }

    @Operation( summary= "Busca uma reserva pelo Id", description= "Serviço utilizado para buscar uma reserva pelo Id.")
    @GetMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Reserva reserva  = obterReservaPeloId.execute(id);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @Operation( summary= "Busca uma reserva pelo restaurante e pela data", description= "Serviço utilizado para buscar uma reserva pelo restaurante e pela data.")
    @GetMapping(value="/restaurante/{idRestaurante}/data/{data}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findByMesa_IdRestaurante(@PathVariable Long idRestaurante, @PathVariable LocalDate data) {
        List<Reserva> reservaList  = buscarReservaPorRestauranteEData.execute(idRestaurante, data);
        return new ResponseEntity<>(reservaList, HttpStatus.OK);
    }

    @Operation( summary= "Realiza o checkin de uma reserva", description= "Serviço utilizado para realizar o checkin de uma reserva.")
    @PutMapping(value="/{idReserva}/checkin/{idCliente}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> realizarCheckin(@PathVariable Long idReserva, @PathVariable String idCliente) {
        Reserva reserva  = realizarCheckin.execute(idReserva, idCliente);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @Operation( summary= "Realiza o checkout de uma reserva", description= "Serviço utilizado para realizar o checkout de uma reserva.")
    @PutMapping(value="/{idReserva}/checkout/{idCliente}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> realizarCheckout(@PathVariable Long idReserva, @PathVariable String idCliente) {
        Reserva reserva  = realizarCheckout.execute(idReserva, idCliente);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @Operation( summary= "Cancela uma reserva", description= "Serviço utilizado para cancelar uma reserva.")
    @PutMapping(value="/{idReserva}/cancelar/{idCliente}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> cancelarReserva(@PathVariable Long idReserva, @PathVariable  String idCliente) {
        Reserva reserva  = cancelarReserva.execute(idReserva, idCliente);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @Operation( summary= "Busca reservas ativas pelo cliente", description= "Busca reservas ativas pelo cliente.")
    @GetMapping(value="/cliente/{idCliente}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> buscarReservasAtivasPeloCliente(@PathVariable String idCliente, Pageable pageable) {
        Page<Reserva> reservaPage = buscarReservasAtivasPorCliente.execute(idCliente, pageable);
        return new ResponseEntity<>(reservaPage, HttpStatus.OK);
    }

}
