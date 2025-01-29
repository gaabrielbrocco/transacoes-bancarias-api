package com.java.transacoes_api.controller;

import com.java.transacoes_api.controller.dtos.ContaInputDTO;
import com.java.transacoes_api.entities.Conta;
import com.java.transacoes_api.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Conta> criarConta(@RequestBody ContaInputDTO dto) throws Exception {
        Conta conta = contaService.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> buscarContas() {
        var contas = contaService.buscarContas();
        return ResponseEntity.ok(contas);
    }

    @DeleteMapping("/{contaId}")
    public ResponseEntity<Void> deletarConta(@PathVariable String contaId) throws Exception {
        try {
            contaService.deletarConta(contaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
