package com.fase2.techchallenge.fiap.entity.endereco.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;

    private String numero;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;


}
