package com.fase2.techchallenge.fiap.usecase.mesa;

import com.fase2.techchallenge.fiap.entity.mesa.gateway.MesaGateway;
import com.fase2.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase2.techchallenge.fiap.entity.mesa.model.MesaId;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.infrastructure.mesa.controller.dto.MesaInsertDTO;
import com.fase2.techchallenge.fiap.usecase.restaurante.ObterRestaurantePeloId;
import org.springframework.stereotype.Service;


@Service
public class CriarMesa {
    private final MesaGateway mesaGateway;

    private final ObterRestaurantePeloId obterRestaurantePeloId;

    public CriarMesa(MesaGateway mesaGateway, ObterRestaurantePeloId obterRestaurantePeloId) {
        this.mesaGateway = mesaGateway;
        this.obterRestaurantePeloId = obterRestaurantePeloId;
    }

    public Mesa execute(MesaInsertDTO mesaInsertDTO) {
        Restaurante restaurante = obterRestaurantePeloId.execute(mesaInsertDTO.getIdRestaurante());
        Mesa mesa = new Mesa(new MesaId(restaurante),mesaInsertDTO.getCapacidade(), mesaInsertDTO.getTipo(), "ATIVO");

        return this.mesaGateway.create(mesa);
    }
}
