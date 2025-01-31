package com.java.transacoes_api.movimentacao.exceptions;

public class UsuarioPossuiContaException extends RuntimeException {
    public UsuarioPossuiContaException(String message) {
        super(message);
    }
}