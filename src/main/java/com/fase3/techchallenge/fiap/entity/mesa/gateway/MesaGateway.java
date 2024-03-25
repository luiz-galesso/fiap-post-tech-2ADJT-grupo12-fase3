package com.fase3.techchallenge.fiap.entity.mesa.gateway;

import com.fase3.techchallenge.fiap.entity.mesa.model.Mesa;
import com.fase3.techchallenge.fiap.entity.mesa.model.MesaId;
import com.fase3.techchallenge.fiap.infrastructure.mesa.repository.MesaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MesaGateway {

    private MesaRepository mesaRepository;

    public MesaGateway(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }
    public Mesa create(Mesa mesa){
        return this.mesaRepository.save(mesa);
    }

    public Mesa update(Mesa mesa){
        return this.mesaRepository.save(mesa);
    }

    public Optional<Mesa> findById(MesaId mesaId){
        return this.mesaRepository.findById(mesaId);
    }

}