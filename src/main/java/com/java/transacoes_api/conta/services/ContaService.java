package com.java.transacoes_api.conta.services;

import com.java.transacoes_api.conta.controller.dtos.ContaInputDTO;
import com.java.transacoes_api.conta.entities.Conta;
import com.java.transacoes_api.conta.exceptions.ContaNaoEncontradaException;
import com.java.transacoes_api.conta.exceptions.UsuarioNaoEncontradoException;
import com.java.transacoes_api.conta.repository.ContaRepository;
import com.java.transacoes_api.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Conta criarConta(ContaInputDTO dto)  throws Exception {
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

    public List<Conta> buscarContas() {
        return contaRepository.findAll();
    }

    public void deletarConta(String contaId) throws Exception {
        var id = Long.parseLong(contaId);

        var contaExistente = contaRepository.findById(id)
                .orElseThrow(ContaNaoEncontradaException::new);

        contaRepository.deleteById(id);
    }
}
