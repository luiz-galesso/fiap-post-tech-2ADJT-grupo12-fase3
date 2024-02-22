package com.fase2.techchallenge.fiap.entity.cliente.model;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;

import java.util.Date;

public class Cliente {
    private String email;
    private String nome;
    private String situacao;
    private Date dataRegistro;
    private Date dataNascimento;
    private Endereco endereco;
}
