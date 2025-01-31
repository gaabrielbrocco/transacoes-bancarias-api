package com.java.transacoes_api.conta.controller.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaInputDTO(String numeroConta, String nome, BigDecimal saldo, LocalDateTime dataCriacao, Long usuarioId) {
}
