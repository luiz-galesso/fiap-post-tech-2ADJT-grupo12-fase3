package com.fase3.techchallenge.fiap.entity.mesa.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_mesa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Mesa {

    @EmbeddedId
    private MesaId id;

    private Integer capacidade;

    private String tipo;

    private String situacao;

}
