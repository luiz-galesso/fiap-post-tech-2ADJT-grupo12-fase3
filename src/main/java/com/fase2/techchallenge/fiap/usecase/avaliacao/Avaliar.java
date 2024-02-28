package com.fase2.techchallenge.fiap.usecase.avaliacao;

import com.fase2.techchallenge.fiap.entity.avaliacao.gateway.AvaliacaoGateway;
import com.fase2.techchallenge.fiap.entity.avaliacao.model.Avaliacao;
import com.fase2.techchallenge.fiap.entity.reserva.gateway.ReservaGateway;
import com.fase2.techchallenge.fiap.entity.reserva.model.Reserva;
import com.fase2.techchallenge.fiap.infrastructure.avaliacao.controller.dto.AvaliacaoInsertDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class Avaliar{

        private final AvaliacaoGateway avaliacaoGateway;
        private final ReservaGateway reservaGateway;
        public Avaliar(AvaliacaoGateway avaliacaoGateway, ReservaGateway reservaGateway) {
            this.avaliacaoGateway = avaliacaoGateway;
            this.reservaGateway = reservaGateway;
        }

        public Avaliacao execute(AvaliacaoInsertDTO avaliacaoDTO) {

            Reserva reserva = reservaGateway.findById(avaliacaoDTO.getIdReserva()).orElseThrow();

            Avaliacao avaliacao =
                    new Avaliacao(reserva,
                            avaliacaoDTO.getValor(),
                            LocalDateTime.now()
                    );

            return this.avaliacaoGateway.create(avaliacao);
        }
}
