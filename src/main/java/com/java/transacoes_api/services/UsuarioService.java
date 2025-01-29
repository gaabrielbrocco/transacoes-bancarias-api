package com.java.transacoes_api.services;

import com.java.transacoes_api.controller.dtos.UsuarioInputDTO;
import com.java.transacoes_api.entities.Usuario;
import com.java.transacoes_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(UsuarioInputDTO dto) {
        var usuario = new Usuario(dto.nome(), dto.email());

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(String usuarioId) throws Exception {
        var id = Long.parseLong(usuarioId);

        var usuarioExistente = usuarioRepository.findById(id);

        if ( !usuarioExistente.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarUsuarios() {return usuarioRepository.findAll();}
}
