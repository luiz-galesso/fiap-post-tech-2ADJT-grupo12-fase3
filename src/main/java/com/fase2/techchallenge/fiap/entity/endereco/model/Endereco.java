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

  public Endereco(String logradouro, String numero, Long cep, String cidade, String estado) {
    if (logradouro != null)
      this.logradouro = logradouro;

    if (numero != null)
      this.numero = numero;

    if (cep != null)
      this.cep = cep;

    if (cidade != null)
      this.cidade = cidade;

    if (estado != null)
      this.estado = estado;
    }
}
