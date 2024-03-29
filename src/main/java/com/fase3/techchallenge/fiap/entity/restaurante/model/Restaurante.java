package com.fase3.techchallenge.fiap.entity.restaurante.model;

import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="tb_restaurante")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_generator")
    @SequenceGenerator(name = "restaurante_generator", sequenceName = "restaurante_sequence", allocationSize = 1)
    private Long id;

    private String nome;

    private Long cnpj;

    private Endereco endereco;

    private String tipoCulinaria;

    private Integer capacidade;

    private String situacao;

    /*TODO necessario melhorar talvez colocar em uma tabela somente disso*/
    private String horarioFuncionamento;

    @CreationTimestamp
    private LocalDateTime dataRegistro;

    public Restaurante(String nome, Long cnpj, Endereco endereco, String tipoCulinaria, Integer capacidade, String situacao, String horarioFuncionamento, LocalDateTime dataRegistro) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.tipoCulinaria = tipoCulinaria;
        this.capacidade = capacidade;
        this.situacao = situacao;
        this.horarioFuncionamento = horarioFuncionamento;
        this.dataRegistro = dataRegistro;
    }

    @PrePersist
    public void prePersist(){
        var timestamp = LocalDateTime.now();
        dataRegistro = timestamp;
    }

    public Restaurante(Endereco endereco) {
        this.endereco = endereco;
    }
}
