package com.java.transacoes_api.conta.repository;

import com.java.transacoes_api.conta.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(String numeroConta);
    boolean existsByUsuarioId(Long usuarioId);
}
