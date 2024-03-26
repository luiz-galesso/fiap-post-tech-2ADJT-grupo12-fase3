package com.fase3.techchallenge.fiap.infrastructure.restaurante.controller.dto;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteUpdateDTO {
    private String nome;
    private Long cnpj;
    private Endereco endereco;
    private String tipoCulinaria;
    private Integer capacidade;
    private String situacao;
    private String horarioFuncionamento;
}