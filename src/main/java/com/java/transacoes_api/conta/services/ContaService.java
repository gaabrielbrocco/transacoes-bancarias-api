package com.java.transacoes_api.conta.services;

import com.java.transacoes_api.conta.controller.dtos.ContaInputDTO;
import com.java.transacoes_api.conta.entities.Conta;
import com.java.transacoes_api.conta.exceptions.ContaNaoEncontradaException;
import com.java.transacoes_api.conta.exceptions.UsuarioNaoEncontradoException;
import com.java.transacoes_api.conta.repository.ContaRepository;
import com.java.transacoes_api.movimentacao.repository.MovimentacaoRepository;
import com.java.transacoes_api.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public Conta criarConta(ContaInputDTO dto) throws Exception {
        var usuarioExistente = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(UsuarioNaoEncontradoException::new);

        var conta = new Conta(
                dto.numeroConta(),
                dto.nome(),
                dto.saldo(),
                LocalDateTime.now(),
                usuarioExistente
        );

        return contaRepository.save(conta);
    }

    public Page<Conta> buscarContas(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }

    public void deletarConta(String contaId) throws Exception {
        var id = Long.parseLong(contaId);

        var contaExistente = contaRepository.findById(id)
                .orElseThrow(ContaNaoEncontradaException::new);

        boolean possuiMovimentacao = movimentacaoRepository.existsByContaId(id);

        if (possuiMovimentacao) {
            throw new RuntimeException("Não é permitida a exclusão da conta com movimentações realizadas");
        }

        contaRepository.deleteById(id);
    }
}
