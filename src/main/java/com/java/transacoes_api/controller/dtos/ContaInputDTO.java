package com.java.transacoes_api.controller.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaInputDTO(String numeroConta, String nome, BigDecimal saldoInicial, LocalDateTime dataCriacao, Long usuarioId) {
}
