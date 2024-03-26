package com.fase3.techchallenge.fiap.infrastructure.cliente.controller;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase3.techchallenge.fiap.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase3.techchallenge.fiap.usecase.cliente.AtualizarCliente;
import com.fase3.techchallenge.fiap.usecase.cliente.CadastrarCliente;
import com.fase3.techchallenge.fiap.usecase.cliente.ObterClientePeloId;
import com.fase3.techchallenge.fiap.usecase.cliente.RemoverClientePeloId;
import com.fase3.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase3.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Serviços para manipular os clientes")
public class ClienteController {

    private final CadastrarCliente cadastrarCliente;
    private final AtualizarCliente atualizarCliente;
    private final ObterClientePeloId obterClientePeloId;
    private final RemoverClientePeloId removerClientePeloId;

    public ClienteController(CadastrarCliente cadastrarCliente, AtualizarCliente atualizarCliente, ObterClientePeloId obterClientePeloId, RemoverClientePeloId removerClientePeloId) {
        this.cadastrarCliente = cadastrarCliente;
        this.atualizarCliente = atualizarCliente;
        this.obterClientePeloId = obterClientePeloId;
        this.removerClientePeloId = removerClientePeloId;
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
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        try {
            var clienteRetorno = atualizarCliente.execute(id, clienteUpdateDTO);
            return new ResponseEntity<>(clienteRetorno, HttpStatus.ACCEPTED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca o cliente pelo Id", description = "Serviço utilizado para buscar o cliente pelo Id.")
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

    @Operation(summary = "Remove o cliente pelo Id", description = "Serviço utilizado para remover o cliente pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            var cliente = removerClientePeloId.execute(id);
            return new ResponseEntity<>("Cliente Removido", HttpStatus.OK);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

}
