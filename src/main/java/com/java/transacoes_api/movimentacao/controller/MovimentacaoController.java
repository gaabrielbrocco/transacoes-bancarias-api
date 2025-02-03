package com.java.transacoes_api.movimentacao.controller;

import com.java.transacoes_api.blockchain.Block;
import com.java.transacoes_api.movimentacao.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.movimentacao.controller.dtos.MovimentacoesDTO;
import com.java.transacoes_api.movimentacao.entities.Movimentacao;
import com.java.transacoes_api.conta.services.ContaService;
import com.java.transacoes_api.movimentacao.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
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

    @GetMapping("")
    public ResponseEntity<List<Movimentacao>> buscarTodasMovimentacoes() {
        var movimentacoes = movimentacaoService.buscarTodasMovimentacoes();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/blockchain")
    public List<Block> visualizarBlockchain() {
        return movimentacaoService.visualizarBlockchain();
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<Movimentacao>> buscarMovimentacoesPorConta(@PathVariable String contaId) throws Exception {

        List<Movimentacao> movimentacoes = movimentacaoService.buscarMovimentacoesPorConta(contaId);
        return ResponseEntity.ok(movimentacoes);

    }
}
