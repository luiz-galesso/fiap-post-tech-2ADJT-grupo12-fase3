package com.fase2.techchallenge.fiap.entity.cliente.model;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="tb_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    private String email;
    private String nome;
    private String situacao;
    private LocalDate dataRegistro;
    private LocalDate dataNascimento;
    private Endereco endereco;
}
