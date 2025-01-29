package com.java.transacoes_api.controller;

import com.java.transacoes_api.controller.dtos.UsuarioInputDTO;
import com.java.transacoes_api.entities.Usuario;
import com.java.transacoes_api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioInputDTO dto) {
        var usuario = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") String usuarioId) throws Exception {
        try {

        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarUsuarios() {
        var usuarios = usuarioService.buscarUsuarios();

        return ResponseEntity.ok(usuarios);
    }
}
