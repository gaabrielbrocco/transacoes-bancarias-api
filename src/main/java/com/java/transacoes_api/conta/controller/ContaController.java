package com.java.transacoes_api.conta.controller;

import com.java.transacoes_api.conta.controller.dtos.ContaInputDTO;
import com.java.transacoes_api.conta.entities.Conta;
import com.java.transacoes_api.conta.exceptions.UsuarioNaoEncontradoException;
import com.java.transacoes_api.conta.services.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Conta")
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Operation(summary = "Criar conta", description = "Criar conta vinculada a um usuário")
    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody ContaInputDTO dto) {
        try {
            Conta conta = contaService.criarConta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(conta);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Buscar todas as contas", description = "Buscar todas as contas no banco de dados")
    @GetMapping
    public ResponseEntity<Page<Conta>> buscarContas(Pageable pageable) {
        Page<Conta> contas = contaService.buscarContas(pageable);
        return ResponseEntity.ok(contas);
    }

    @Operation(summary = "Deletar conta", description = "Deletar uma conta no banco de dados")
    @DeleteMapping("/{contaId}")
    public ResponseEntity<Void> deletarConta(@PathVariable String contaId) throws Exception {

        contaService.deletarConta(contaId);
        return ResponseEntity.noContent().build();

    }
}
