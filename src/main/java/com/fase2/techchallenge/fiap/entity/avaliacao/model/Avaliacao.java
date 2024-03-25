package com.fase2.techchallenge.fiap.entity.avaliacao.model;

import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="tb_avaliacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avaliacao_generator")
    @SequenceGenerator(name = "avaliacao_generator", sequenceName = "avaliacao_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Reserva reserva;

    private Integer valor;

    private LocalDateTime dataRegistro;


    public Avaliacao(Reserva reserva, Integer avaliacao, LocalDateTime dataRegistro) {
        this.reserva = reserva;
        this.valor = valor;
        this.dataRegistro = dataRegistro;
    }
}
