package com.fase2.techchallenge.fiap.infrastructure.cliente.controller.dto;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClienteUpdateDTO {

    private String nome;
    private String situacao;
    private LocalDate dataNascimento;
    private Endereco endereco;
}
