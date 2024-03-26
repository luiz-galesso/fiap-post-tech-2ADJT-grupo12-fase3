package com.fase3.techchallenge.fiap.usecase.reserva;

import com.fase3.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase3.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase3.techchallenge.fiap.usecase.restaurante.ObterRestaurantePeloId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BuscarReservaPorRestauranteEData {

    private final ObterRestaurantePeloId obterRestaurantePeloId;

    private final ReservaGateway reservaGateway;

    public List<Reserva> execute (Long idRestaurante, LocalDate data){

        Restaurante restaurante = obterRestaurantePeloId.execute(idRestaurante);

        List<Reserva> reservaList = reservaGateway.findByMesaIdRestauranteAndDataHoraInicioBetween(restaurante, data.atStartOfDay(), data.plusDays(1).atStartOfDay());

        return reservaList;
    }
}
