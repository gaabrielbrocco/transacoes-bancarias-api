package com.java.transacoes_api.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class DefaultException extends RuntimeException {
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Ocorreu um erro inesperado.");

        return problemDetail;
    }
}