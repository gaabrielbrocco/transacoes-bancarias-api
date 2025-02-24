package com.java.transacoes_api.usuario.services;

import com.java.transacoes_api.conta.exceptions.UsuarioNaoEncontradoException;
import com.java.transacoes_api.conta.repository.ContaRepository;
import com.java.transacoes_api.usuario.controller.dtos.UsuarioInputDTO;
import com.java.transacoes_api.usuario.entities.Usuario;
import com.java.transacoes_api.usuario.exceptions.EmailJaCadastradoException;
import com.java.transacoes_api.usuario.repository.UsuarioRepository;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaRepository contaRepository;


    public Usuario criarUsuario(UsuarioInputDTO dto, Span span) {

        span.addEvent("criar-usuario-service");
        var emailExistente = usuarioRepository.findByEmail(dto.email());
        if (emailExistente.isPresent()) {
            throw new EmailJaCadastradoException("E-mail já cadastrado: " + dto.email());
        }

        var usuario = new Usuario(dto.nome(), dto.email());
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(String usuarioId) {
        var id = Long.parseLong(usuarioId);

        var usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        boolean possuiContaVinculada = contaRepository.existsByUsuarioId(id);

        if (possuiContaVinculada) {
            throw new RuntimeException("Não é permitida a exclusão de usuário com conta vinculada.");
        }

        usuarioRepository.deleteById(id);
    }

    public Page<Usuario> buscarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
}
