package com.fase3.techchallenge.fiap.utils;


import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteHelper {
    public static Cliente gerarCliente(String email, String nome, String situacao, LocalDate dataNascimento) {

        return Cliente.builder()
                .email(email)
                .nome(nome)
                .situacao(situacao)
                .dataRegistro(LocalDateTime.now())
                .dataNascimento(dataNascimento)
                .endereco(Endereco.builder()
                        .logradouro("Rua Fidencio Ramos")
                        .numero("408")
                        .bairro("Vila Olimpia")
                        .complemento("13 A")
                        .cep(02456314L)
                        .cidade("São Paulo")
                        .estado("SP")
                        .referencia("Proximo ao Shopping Vila Olimpia")
                        .build())
                .build();
    }
}
