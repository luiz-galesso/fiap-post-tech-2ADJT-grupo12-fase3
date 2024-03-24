package com.fase2.techchallenge.fiap.infrastructure.cliente.controller;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase2.techchallenge.fiap.usecase.cliente.AtualizarCliente;
import com.fase2.techchallenge.fiap.usecase.cliente.CadastrarCliente;
import com.fase2.techchallenge.fiap.usecase.cliente.ObterClientePeloId;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Serviços para manipular os clientes")
public class ClienteController {

    private final CadastrarCliente cadastrarCliente;
    private final AtualizarCliente atualizarCliente;
    private final ObterClientePeloId obterClientePeloId;

    public ClienteController(CadastrarCliente cadastrarCliente, AtualizarCliente atualizarCliente, ObterClientePeloId obterClientePeloId) {
        this.cadastrarCliente = cadastrarCliente;
        this.atualizarCliente = atualizarCliente;
        this.obterClientePeloId = obterClientePeloId;
    }

    @Operation(summary = "Realiza um novo cadastro de cliente", description = "Serviço utilizado para cadastro do cliente.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ClienteInsertDTO clienteInsertDTO) {
        try {
            Cliente cliente = cadastrarCliente.execute(clienteInsertDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Altera os dados do cliente", description = "Serviço utilizado para alterar os dados do cliente.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ClienteInsertDTO clienteInsertDTO) {
        try {
            var clienteRetorno = atualizarCliente.execute(id, clienteInsertDTO);
            return new ResponseEntity<>(clienteRetorno, HttpStatus.ACCEPTED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca um cliente pelo Id", description = "Serviço utilizado para buscar um cliente pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            var cliente = obterClientePeloId.execute(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityNotFoundException.getMessage());
        }
    }

}
