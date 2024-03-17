package com.fase2.techchallenge.fiap.usecase.avaliacao;

import com.fase2.techchallenge.fiap.entity.avaliacao.gateway.AvaliacaoGateway;
import com.fase2.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.avaliacao.controller.dto.AvaliacaoInsertDTO;
import com.fase2.techchallenge.fiap.usecase.exception.BussinessErrorException;
import com.fase2.techchallenge.fiap.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class Avaliar{

        private final AvaliacaoGateway avaliacaoGateway;
        private final ReservaGateway reservaGateway;
        public Avaliar(AvaliacaoGateway avaliacaoGateway, ReservaGateway reservaGateway) {
            this.avaliacaoGateway = avaliacaoGateway;
            this.reservaGateway = reservaGateway;
        }

        public Avaliacao execute(AvaliacaoInsertDTO avaliacaoDTO) {

            Reserva reserva = reservaGateway.findById(avaliacaoDTO.getIdReserva()).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada."));
            if (avaliacaoDTO.getValor() < 0 || avaliacaoDTO.getValor() > 5) {
                throw new BussinessErrorException("Valor inválido para avaliação.");
            }
            Avaliacao avaliacao =
                    new Avaliacao(reserva,
                            avaliacaoDTO.getValor(),
                            LocalDateTime.now()
                    );

            return this.avaliacaoGateway.create(avaliacao);
        }
}
