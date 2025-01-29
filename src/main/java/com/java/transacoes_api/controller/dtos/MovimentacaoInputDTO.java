package com.java.transacoes_api.controller.dtos;

import com.java.transacoes_api.entities.Movimentacao;

import java.math.BigDecimal;

public record MovimentacaoInputDTO(String contaOrigem, String contaDestino, BigDecimal valor) {
    public Movimentacao toMovimentacaoEntity() {
        return new Movimentacao(contaOrigem, contaDestino, valor);
    }
}
