package com.java.transacoes_api.movimentacao.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.transacoes_api.blockchain.Block;
import com.java.transacoes_api.blockchain.Blockchain;
import com.java.transacoes_api.conta.exceptions.ContaNaoEncontradaException;
import com.java.transacoes_api.movimentacao.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.movimentacao.entities.Movimentacao;
import com.java.transacoes_api.movimentacao.entities.TipoMovimentacao;
import com.java.transacoes_api.conta.repository.ContaRepository;
import com.java.transacoes_api.movimentacao.repository.MovimentacaoRepository;
import com.java.transacoes_api.usuario.repository.UsuarioRepository;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Blockchain blockchain = new Blockchain();

    @Transactional
    public Movimentacao transferencia(MovimentacaoInputDTO dto) throws Exception {
        var contaOrigem = contaRepository.findByNumeroConta(dto.contaOrigem())
                .orElseThrow(ContaNaoEncontradaException::new);

        var contaDestino = contaRepository.findByNumeroConta(dto.contaDestino())
                .orElseThrow(ContaNaoEncontradaException::new);

        if (dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor da transferência deve ser maior que zero");
        }

        if (contaOrigem.getSaldo().compareTo(dto.valor()) < 0) {
            throw new Exception("Saldo insuficiente para a transferência");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(dto.valor()));
        contaDestino.setSaldo(contaDestino.getSaldo().add(dto.valor()));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Movimentacao movimentacaoDebito = new Movimentacao();
        movimentacaoDebito.setValor(dto.valor().negate());
        movimentacaoDebito.setDescricao("Transferência enviada para " + dto.contaDestino());
        movimentacaoDebito.setConta(contaOrigem);
        movimentacaoDebito.setData(LocalDateTime.now().minusHours(3));
        movimentacaoDebito.setTipo(new TipoMovimentacao(1L, "Débito"));

        Movimentacao movimentacaoCredito = new Movimentacao();
        movimentacaoCredito.setValor(dto.valor());
        movimentacaoCredito.setDescricao("Transferência recebida de " + dto.contaOrigem());
        movimentacaoCredito.setConta(contaDestino);
        movimentacaoCredito.setData(LocalDateTime.now().minusHours(3));
        movimentacaoCredito.setTipo(new TipoMovimentacao(2L, "Crédito"));

        movimentacaoRepository.save(movimentacaoDebito);
        movimentacaoRepository.save(movimentacaoCredito);

        Map<String, Object> mapDebito = objectMapper.convertValue(movimentacaoDebito, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> mapCredito = objectMapper.convertValue(movimentacaoCredito, new TypeReference<Map<String, Object>>() {});

        blockchain.addBlock(mapDebito, "Débito");
        blockchain.addBlock(mapCredito, "Crédito");

        if (!blockchain.isValid()) {
            throw new Exception("A blockchain foi corrompida");
        }

        String nomeOrigem = contaOrigem.getUsuario().getNome();
        String emailOrigem = contaOrigem.getUsuario().getEmail();
        String nomeDestino = contaDestino.getUsuario().getNome();
        String emailDestino = contaDestino.getUsuario().getEmail();

        enviarEmail(emailOrigem, nomeDestino, nomeOrigem, dto.valor(), dto.contaOrigem(), dto.contaDestino(), movimentacaoDebito.getData());
        enviarEmail(emailDestino, nomeDestino, nomeOrigem, dto.valor(), dto.contaOrigem(), dto.contaDestino(), movimentacaoCredito.getData());


        return movimentacaoDebito;
    }

    public List<Movimentacao> buscarTodasMovimentacoes() {
        return movimentacaoRepository.findAll();
    }


    private String loadFileFromResources(String filePath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo não encontrado: " + filePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public void enviarEmail(String email, String nomeDestinatario, String nomeRemetente, BigDecimal valor, String contaOrigem, String contaDestino, LocalDateTime dataHora) throws Exception {
        String template = loadFileFromResources("templates/email-transferencia.html");

        template = template.replace("{{nomeDestinatario}}", nomeDestinatario);
        template = template.replace("{{nomeRemetente}}", nomeRemetente);
        template = template.replace("{{valor}}", valor.toString());
        template = template.replace("{{contaOrigem}}", contaOrigem);
        template = template.replace("{{contaDestino}}", contaDestino);
        template = template.replace("{{dataHora}}", dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        Resend resend = new Resend("re_ijCdmN6g_J3FtL77CJ1S3yfBN4SY2ULJG");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Banco <contato@travelerbrasil.com>")
                .to(email)
                .html(template)
                .subject("Transferência Bancária realizada")
                .build();

        try {
            resend.emails().send(params);
        } catch (ResendException e) {
            e.printStackTrace();
            throw new Exception("Erro ao enviar e-mail.");
        }
    }

    public List<Block> visualizarBlockchain() {
        return blockchain.getChain();
    }


    public List<Movimentacao> buscarMovimentacoesPorConta(String contaId) throws Exception {
        var id = Long.parseLong(contaId);

        var contaExistente = contaRepository.findById(id)
                .orElseThrow(ContaNaoEncontradaException::new);

        return movimentacaoRepository.findByContaId(id);
    }

}
