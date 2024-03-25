package com.fase2.techchallenge.fiap.infrastructure.restaurante.utils;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.restaurante.repository.RestauranteRepository;

public abstract class RestauranteHelper {

    public static Restaurante gerarRestaurante(Long id) {
        return Restaurante.builder()
                .id(id)
                .nome("Jardins Grill")
                .cnpj(52123949000182L)
                .endereco(Endereco.builder()
                                .logradouro("Av. Silva Jardim")
                                .numero("780A")
                                .cep(82887417L)
                                .cidade("Umuarama")
                                .estado("PR")
                                .referencia("Posto Ipiranga")
                                .complemento("Andar Superior")
                                .bairro("centro")
                                .build()
                            )
                .tipoCulinaria("steakhouse")
                .capacidade(280)
                .situacao("ATIVO")
                .horarioFuncionamento("19:00 - 23:00").build();

    }

    public static Restaurante registrarRestaurante(RestauranteRepository restauranteRepository, Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }
}
