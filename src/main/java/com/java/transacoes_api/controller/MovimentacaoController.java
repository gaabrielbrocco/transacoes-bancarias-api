package com.java.transacoes_api.controller;

import com.java.transacoes_api.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.entities.Conta;
import com.java.transacoes_api.entities.Movimentacao;
import com.java.transacoes_api.entities.Usuario;
import com.java.transacoes_api.services.ContaService;
import com.java.transacoes_api.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
