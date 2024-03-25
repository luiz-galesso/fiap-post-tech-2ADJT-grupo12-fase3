package com.fase3.techchallenge.fiap.infrastructure.reserva.utils;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import com.fase3.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase3.techchallenge.fiap.entity.mesa.model.MesaId;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.infrastructure.restaurante.utils.RestauranteHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaHelper {

    public static Reserva gerarReserva(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, String situacao) {

        Mesa mesa = new Mesa();
        Restaurante restaurante = RestauranteHelper.gerarRestaurante(1L);
        mesa.setId(new MesaId(restaurante, 1L));

        Cliente cliente = new Cliente();
        cliente.setEmail("maria.santos@example.com");

       return Reserva.builder()
               .id(id)
               .dataHoraInicio(dataInicio)
               .dataHoraFinal(dataFim)
               .situacao(situacao)
               .mesa(mesa)
               .cliente(cliente)
               .build();
    }

    public static List<Reserva> gerarLista()
    {
        List<Reserva> reservas = new ArrayList<>();
        LocalDateTime dataInicio = LocalDateTime.of(2024, 3, 20, 11, 30);
        LocalDateTime dataFinal = LocalDateTime.of(2024, 3, 20, 13, 30);
        reservas.add(ReservaHelper.gerarReserva(1L, dataInicio, dataFinal, "RESERVADO"));
        return reservas;
    }
}
