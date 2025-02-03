package com.java.transacoes_api.movimentacao.repository;

import com.java.transacoes_api.movimentacao.entities.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    boolean existsByContaId(Long contaId);

    Page<Movimentacao> findByContaId(Long contaId, Pageable pageable);

    Page<Movimentacao> findAll(Pageable pageable);
}
