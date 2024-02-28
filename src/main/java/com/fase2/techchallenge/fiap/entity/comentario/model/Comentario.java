package com.fase2.techchallenge.fiap.entity.comentario.model;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="tb_comentario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comentario_generator")
    @SequenceGenerator(name = "comentario_generator", sequenceName = "comentario_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Reserva reserva;

    private String texto;

    private LocalDateTime dataRegistro;

    public Comentario(Reserva reserva, String texto, LocalDateTime dataRegistro) {
        this.reserva = reserva;
        this.texto = texto;
        this.dataRegistro = dataRegistro;
    }
}
