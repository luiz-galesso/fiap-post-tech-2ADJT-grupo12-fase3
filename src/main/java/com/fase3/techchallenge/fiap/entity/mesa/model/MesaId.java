package com.fase3.techchallenge.fiap.entity.mesa.model;

import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class MesaId implements Serializable {

    @ManyToOne
    private Restaurante restaurante;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mesa_generator")
    @SequenceGenerator(name = "mesa_generator", sequenceName = "mesa_sequence", allocationSize = 1)
    private Long idMesa;

    public MesaId(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}
