package com.java.transacoes_api.usuario.controller;

import com.java.transacoes_api.usuario.controller.dtos.UsuarioInputDTO;
import com.java.transacoes_api.usuario.entities.Usuario;
import com.java.transacoes_api.usuario.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Usuário")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Criar usuário", description = "Criar um novo usuário no banco de dados")
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid UsuarioInputDTO dto) {
        var usuario = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Operation(summary = "Buscar todos os usuários", description = "Buscar todos os usuários no banco de dados")
    @GetMapping
    public ResponseEntity<Page<Usuario>> buscarUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.buscarUsuarios(pageable);

        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Deletar usuário", description = "Deletar um usuário no banco de dados")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") String usuarioId) {

        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
