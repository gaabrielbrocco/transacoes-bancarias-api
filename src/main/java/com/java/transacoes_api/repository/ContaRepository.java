package com.java.transacoes_api.repository;

import com.java.transacoes_api.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(String numeroConta);

}
