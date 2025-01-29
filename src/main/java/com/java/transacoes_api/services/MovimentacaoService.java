package com.java.transacoes_api.services;

import com.java.transacoes_api.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.entities.Conta;
import com.java.transacoes_api.entities.Movimentacao;
import com.java.transacoes_api.entities.TipoMovimentacao;
import com.java.transacoes_api.repository.ContaRepository;
import com.java.transacoes_api.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaRepository contaRepository;


    @Transactional
    public Movimentacao transferencia(MovimentacaoInputDTO dto) throws Exception {
        var contaOrigem = contaRepository.findByNumeroConta(dto.contaOrigem())
                .orElseThrow(() -> new Exception("Conta de origem não encontrada"));

        var contaDestino = contaRepository.findByNumeroConta(dto.contaDestino())
                .orElseThrow(() -> new Exception("Conta de destino não encontrada"));

        if (dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor da transferência deve ser maior que zero");
        }

        if (contaOrigem.getSaldoInicial().compareTo(dto.valor()) < 0) {
            throw new Exception("Saldo insuficiente para a transferência");
        }

        contaOrigem.setSaldoInicial(contaOrigem.getSaldoInicial().subtract(dto.valor()));
        contaDestino.setSaldoInicial(contaDestino.getSaldoInicial().add(dto.valor()));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        Movimentacao movimentacaoDebito = new Movimentacao();
        movimentacaoDebito.setValor(dto.valor().negate());
        movimentacaoDebito.setDescricao("Transferência para conta " + dto.contaDestino());
        movimentacaoDebito.setConta(contaOrigem);
        movimentacaoDebito.setData(LocalDateTime.now());
        movimentacaoDebito.setTipo(new TipoMovimentacao(1L, "Débito"));

        Movimentacao movimentacaoCredito = new Movimentacao();
        movimentacaoCredito.setValor(dto.valor());
        movimentacaoCredito.setDescricao("Transferência recebida de conta " + dto.contaOrigem());
        movimentacaoCredito.setConta(contaDestino);
        movimentacaoDebito.setData(LocalDateTime.now());
        movimentacaoCredito.setTipo(new TipoMovimentacao(2L, "Crédito"));

        movimentacaoRepository.save(movimentacaoDebito);
        movimentacaoRepository.save(movimentacaoCredito);

        return movimentacaoDebito;
    }
}
