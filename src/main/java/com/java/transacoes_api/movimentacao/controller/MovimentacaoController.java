package com.java.transacoes_api.movimentacao.controller;

import com.java.transacoes_api.blockchain.Block;
import com.java.transacoes_api.movimentacao.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.movimentacao.entities.Movimentacao;
import com.java.transacoes_api.conta.services.ContaService;
import com.java.transacoes_api.movimentacao.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private ContaService contaService;

    @PostMapping("/transferencia")
    public ResponseEntity<Movimentacao> transferencia(@RequestBody MovimentacaoInputDTO dto) throws Exception {
        Movimentacao movimentacao = movimentacaoService.transferencia(dto);
        return new ResponseEntity<>(movimentacao, HttpStatus.OK);
    }

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<Movimentacao>> buscarTodasMovimentacoes() {
        var movimentacoes = movimentacaoService.buscarTodasMovimentacoes();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/blockchain")
    public List<Block> visualizarBlockchain() {
        return movimentacaoService.visualizarBlockchain();
    }
}
