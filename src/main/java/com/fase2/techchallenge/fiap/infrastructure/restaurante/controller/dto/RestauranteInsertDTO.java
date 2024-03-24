package com.fase2.techchallenge.fiap.infrastructure.restaurante.controller.dto;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestauranteInsertDTO {

    private String nome;

    private Long cnpj;

    private Endereco endereco;

    private String tipoCulinaria;

    private Integer capacidade;

    private String situacao;
    private String horarioFuncionamento;
}
