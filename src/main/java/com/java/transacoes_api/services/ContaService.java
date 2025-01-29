package com.java.transacoes_api.services;

import com.java.transacoes_api.controller.dtos.ContaInputDTO;
import com.java.transacoes_api.entities.Conta;
import com.java.transacoes_api.repository.ContaRepository;
import com.java.transacoes_api.repository.UsuarioRepository;
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
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        var conta = new Conta(
                dto.numeroConta(),
                dto.nome(),
                dto.saldoInicial(),
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

        var contaExistente = contaRepository.findById(id);

        if (!contaExistente.isPresent()) {
            throw new Exception("Conta não encontrada");
        }

        contaRepository.deleteById(id);
    }
}
