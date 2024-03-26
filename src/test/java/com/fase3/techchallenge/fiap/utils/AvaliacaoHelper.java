package com.fase3.techchallenge.fiap.utils;

import com.fase3.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase3.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase3.techchallenge.fiap.entity.mesa.model.MesaId;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.avaliacao.repository.AvaliacaoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AvaliacaoHelper {
    public static Avaliacao gerarAvaliacao(Long id) {
        return Avaliacao
                .builder()
                .id(id)
                .reserva(Reserva
                        .builder()
                        .id(1L)
                        .mesa(Mesa
                                .builder()
                                .capacidade(4)
                                .situacao("ATIVO")
                                .tipo("INTERNO")
                                .id(MesaId
                                        .builder()
                                        .idMesa(1L)
                                        .restaurante(Restaurante.builder()
                                                .id(id)
                                                .nome("Kotayama Sushi")
                                                .cnpj(5489293000136L)
                                                .endereco(Endereco.builder()
                                                        .logradouro("Rua alberto josé")
                                                        .numero("309A")
                                                        .cep(9360450L)
                                                        .cidade("São Paulo")
                                                        .estado("SP")
                                                        .referencia(null)
                                                        .complemento(null)
                                                        .bairro("Liberdade")
                                                        .build()
                                                )
                                                .tipoCulinaria("Japonesa")
                                                .capacidade(200)
                                                .situacao("ATIVO")
                                                .dataRegistro(LocalDateTime.of(2022,03,18,22,14,42,759345))
                                                .horarioFuncionamento("09:00 as 20:00").build()).build()).build()
                        )
                        .cliente(Cliente.builder()
                                .email("joao.silva@example.com")
                                .nome("João da Silva")
                                .situacao("INATIVO")
                                .dataRegistro(LocalDateTime.now())
                                .dataNascimento(LocalDate.of(1990,5,15))
                                .endereco(Endereco.builder()
                                        .logradouro("Rua das Flores")
                                        .numero("123")
                                        .bairro("Centro")
                                        .complemento("Apto 101")
                                        .cep(30140010L)
                                        .cidade("Belo Horizonte")
                                        .estado("MG")
                                        .referencia("Próximo à Praça Central")
                                        .build())
                                .build())
                        .situacao("ATIVO")
                        .dataHoraInicio(LocalDateTime.of(2024, 3, 20, 11, 30))
                        .dataHoraFinal(LocalDateTime.of(2024, 3, 20, 14, 15))
                        .dataHoraCheckin(null)
                        .dataHoraCheckout(null).build())
                .valor(5)
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static Avaliacao registrarAvaliacao(AvaliacaoRepository avaliacaoRepository, Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }
}
