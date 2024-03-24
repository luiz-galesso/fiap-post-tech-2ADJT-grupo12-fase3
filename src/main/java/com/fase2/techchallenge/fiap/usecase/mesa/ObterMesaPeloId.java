package com.fase2.techchallenge.fiap.usecase.mesa;

import com.fase2.techchallenge.fiap.entity.mesa.gateway.MesaGateway;
import com.fase2.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase2.techchallenge.fiap.entity.mesa.model.MesaId;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import com.fase2.techchallenge.fiap.usecase.restaurante.ObterRestaurantePeloId;
import org.springframework.stereotype.Service;

@Service
public class ObterMesaPeloId {
    private final MesaGateway mesaGateway;

    private final ObterRestaurantePeloId obterRestaurantePeloId;

    public ObterMesaPeloId(MesaGateway mesaGateway, ObterRestaurantePeloId obterRestaurantePeloId) {
        this.mesaGateway = mesaGateway;
        this.obterRestaurantePeloId = obterRestaurantePeloId;
    }

    public Mesa execute(Long idRestaurante, Long idMesa) {
        Restaurante restaurante = obterRestaurantePeloId.execute(idRestaurante);
        return this.mesaGateway.findById(new MesaId(restaurante, idMesa)).orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada."));
    }


}