package com.java.transacoes_api.movimentacao.controller.dtos;

import com.java.transacoes_api.movimentacao.entities.Movimentacao;

import java.math.BigDecimal;

public record MovimentacaoInputDTO(String contaOrigem, String contaDestino, BigDecimal valor) {
    public Movimentacao toMovimentacaoEntity() {
        return new Movimentacao(contaOrigem, contaDestino, valor);
    }
}
