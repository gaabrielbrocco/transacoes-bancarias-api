package com.java.transacoes_api.usuario.controller.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioInputDTO(@NotBlank String nome, @NotBlank String email) {
}
