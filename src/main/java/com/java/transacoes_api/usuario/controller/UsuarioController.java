package com.java.transacoes_api.usuario.controller;

import com.java.transacoes_api.usuario.controller.dtos.UsuarioInputDTO;
import com.java.transacoes_api.usuario.entities.Usuario;
import com.java.transacoes_api.usuario.services.UsuarioService;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    private final Tracer tracer;

    public UsuarioController(OpenTelemetry openTelemetry) {
        this.tracer = openTelemetry.getTracer("meu-app");
    }

    @Operation(summary = "Criar usuário", description = "Criar um novo usuário no banco de dados")
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid UsuarioInputDTO dto) {
        Span span = tracer.spanBuilder("Criar Usuário")
                .setSpanKind(SpanKind.SERVER)
                .startSpan();

        try {
            logger.info("Recebendo requisição para criar usuário: {}", dto);
            var usuario = usuarioService.criarUsuario(dto);
            span.setStatus(StatusCode.OK);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            logger.error("Erro ao criar usuário: {}", e.getMessage(), e);
            span.setStatus(StatusCode.ERROR);
            throw e;
        } finally {
            span.end();
        }
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
