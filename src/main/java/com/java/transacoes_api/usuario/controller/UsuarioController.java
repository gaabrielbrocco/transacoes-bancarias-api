package com.java.transacoes_api.usuario.controller;

import com.java.transacoes_api.conta.exceptions.UsuarioNaoEncontradoException;
import com.java.transacoes_api.usuario.controller.dtos.UsuarioInputDTO;
import com.java.transacoes_api.usuario.entities.Usuario;
import com.java.transacoes_api.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid UsuarioInputDTO dto) {
        var usuario = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") String usuarioId){

        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> buscarUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.buscarUsuarios(pageable);

        return ResponseEntity.ok(usuarios);
    }
}
