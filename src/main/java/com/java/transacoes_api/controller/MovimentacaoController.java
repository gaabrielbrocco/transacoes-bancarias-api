package com.java.transacoes_api.controller;

import com.java.transacoes_api.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.entities.Movimentacao;
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

    @PostMapping("/transferencia")
    public ResponseEntity<Movimentacao> transferencia(@RequestBody MovimentacaoInputDTO dto) throws Exception {
        return new ResponseEntity<>(movimentacaoService.transferencia(dto), HttpStatus.OK);
    }


}
