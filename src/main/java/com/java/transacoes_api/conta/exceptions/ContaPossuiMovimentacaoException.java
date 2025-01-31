package com.java.transacoes_api.conta.exceptions;

public class ContaPossuiMovimentacaoException extends RuntimeException {
  public ContaPossuiMovimentacaoException(String message) {
    super(message);
  }
}