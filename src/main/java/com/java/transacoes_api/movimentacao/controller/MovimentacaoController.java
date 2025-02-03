package com.java.transacoes_api.movimentacao.controller;

import com.java.transacoes_api.blockchain.Block;
import com.java.transacoes_api.movimentacao.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.movimentacao.entities.Movimentacao;
import com.java.transacoes_api.movimentacao.services.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Movimentações")
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;


    @Operation(summary = "Realizar transferência", description = "Realizar uma transferência bancária para outra conta")
    @PostMapping("/transferencia")
    public ResponseEntity<Movimentacao> transferencia(@RequestBody MovimentacaoInputDTO dto) throws Exception {
        Movimentacao movimentacao = movimentacaoService.transferencia(dto);
        return new ResponseEntity<>(movimentacao, HttpStatus.OK);
    }

    @Operation(summary = "Buscar movimentações", description = "Buscar todas as movimentações no banco de dados")
    @GetMapping
    public ResponseEntity<Page<Movimentacao>> buscarTodasMovimentacoes(Pageable pageable) {
        Page<Movimentacao> movimentacoes = movimentacaoService.buscarTodasMovimentacoes(pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @Operation(summary = "Buscar movimentações pela conta", description = "Buscar as movimentações realizadas por uma conta específica")
    @GetMapping("/conta/{contaId}")
    public ResponseEntity<Page<Movimentacao>> buscarMovimentacoesPorConta(
            @PathVariable String contaId, Pageable pageable) throws Exception {

        Page<Movimentacao> movimentacoes = movimentacaoService.buscarMovimentacoesPorConta(contaId, pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @Operation(summary = "Visualizar blockchain", description = "Consultar os registros salvos na blockchain")
    @GetMapping("/blockchain")
    public List<Block> visualizarBlockchain() {
        return movimentacaoService.visualizarBlockchain();
    }

}
