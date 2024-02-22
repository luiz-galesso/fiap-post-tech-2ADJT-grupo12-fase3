package com.fase2.techchallenge.fiap.entity.endereco.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

    private String logradouro;

    private String numero;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;
}
