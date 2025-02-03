package com.java.transacoes_api.conta.controller;

import com.java.transacoes_api.conta.controller.dtos.ContaInputDTO;
import com.java.transacoes_api.conta.entities.Conta;
import com.java.transacoes_api.conta.exceptions.ContaNaoEncontradaException;
import com.java.transacoes_api.conta.exceptions.UsuarioNaoEncontradoException;
import com.java.transacoes_api.conta.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

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

    @GetMapping
    public ResponseEntity<Page<Conta>> buscarContas(Pageable pageable) {
        Page<Conta> contas = contaService.buscarContas(pageable);
        return ResponseEntity.ok(contas);
    }

    @DeleteMapping("/{contaId}")
    public ResponseEntity<Void> deletarConta(@PathVariable String contaId) throws Exception {

            contaService.deletarConta(contaId);
            return ResponseEntity.noContent().build();

    }
}
