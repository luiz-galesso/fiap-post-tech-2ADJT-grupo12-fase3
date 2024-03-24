package com.fase2.techchallenge.fiap.entity.cliente.model;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cliente")
@Data
@Builder(toBuilder = true)
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
