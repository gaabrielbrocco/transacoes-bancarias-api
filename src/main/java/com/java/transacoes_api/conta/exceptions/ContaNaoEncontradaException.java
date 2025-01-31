package com.java.transacoes_api.conta.exceptions;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException() {
        super("Conta não encontrada");
    }
}