package com.fase2.techchallenge.fiap.infrastructure.cliente.controller;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase2.techchallenge.fiap.usecase.cliente.CadastrarCliente;
import com.fase2.techchallenge.fiap.usecase.reserva.Reservar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description="Serviços para manipular os clientes")
public class ClienteController {

    private final CadastrarCliente cadastrarCliente;

    public ClienteController(CadastrarCliente cadastrarCliente) {
        this.cadastrarCliente = cadastrarCliente;
    }

    @Operation( summary= "Realiza um novo cadastro de cliente", description= "Serviço utilizado para realizar uma novo reversa.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ClienteInsertDTO clienteInsertDTO) {
        Cliente cliente = cadastrarCliente.execute(clienteInsertDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    /*@Operation( summary= "Busca uma reserva pelo Id", description= "Serviço utilizado para buscar uma reserva pelo Id.")
    @GetMapping(value="/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Reserva reserva  = obterReservaPeloId.execute(id);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }*/

}
