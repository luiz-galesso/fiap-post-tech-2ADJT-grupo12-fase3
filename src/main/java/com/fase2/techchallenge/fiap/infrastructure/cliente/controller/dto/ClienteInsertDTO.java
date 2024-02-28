package com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClienteInsertDTO {

    private String email;
    private String nome;
    private String situacao;
    private LocalDate dataNascimento;
    private Endereco endereco;
}
