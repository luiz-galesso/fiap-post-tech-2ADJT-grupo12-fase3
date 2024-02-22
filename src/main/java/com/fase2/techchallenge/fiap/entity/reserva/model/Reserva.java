package com.fase2.techchallenge.fiap.entity.reserva.model;

import com.fase2.techchallenge.fiap.entity.mesa.model.Mesa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name="tb_reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_generator")
    @SequenceGenerator(name = "reserva_generator", sequenceName = "reserva_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Mesa mesa;

    private String situacao;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFinal;

    public Reserva(Mesa mesa, String situacao, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFinal) {
        this.mesa = mesa;
        this.situacao = situacao;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFinal = dataHoraFinal;
    }
}
