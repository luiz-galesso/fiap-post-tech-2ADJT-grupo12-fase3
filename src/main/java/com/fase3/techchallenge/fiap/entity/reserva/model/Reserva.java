package com.fase3.techchallenge.fiap.entity.reserva.model;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.entity.mesa.model.Mesa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name="tb_reserva")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_generator")
    @SequenceGenerator(name = "reserva_generator", sequenceName = "reserva_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    private Mesa mesa;

    @ManyToOne
    @NotNull
    private Cliente cliente;

    @NotNull
    private String situacao;

    @NotNull
    private LocalDateTime dataHoraInicio;

    @NotNull
    private LocalDateTime dataHoraFinal;

    private LocalDateTime dataHoraCheckin;

    private LocalDateTime dataHoraCheckout;

    public Reserva(Mesa mesa, Cliente cliente, String situacao, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFinal) {
        this.mesa = mesa;
        this.cliente = cliente;
        this.situacao = situacao;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFinal = dataHoraFinal;
    }
}
