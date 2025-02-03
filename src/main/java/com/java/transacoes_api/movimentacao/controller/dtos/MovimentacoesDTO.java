package com.java.transacoes_api.movimentacao.controller.dtos;

import java.time.LocalDateTime;

public record MovimentacoesDTO(Long contaId,
                               Long tipoId,
                               LocalDateTime dataInicio,
                               LocalDateTime dataFim) {
}
