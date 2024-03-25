package com.fase2.techchallenge.fiap.entity.cliente.model;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tb_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    private String email;

    @NotNull
    private String nome;

    @NotNull
    private String situacao;

    @NotNull
    private LocalDateTime dataRegistro;
    private LocalDate dataNascimento;
    private Endereco endereco;
}
