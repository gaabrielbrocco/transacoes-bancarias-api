package com.java.transacoes_api.services;

import com.java.transacoes_api.controller.dtos.MovimentacaoInputDTO;
import com.java.transacoes_api.entities.Conta;
import com.java.transacoes_api.entities.Movimentacao;
import com.java.transacoes_api.entities.TipoMovimentacao;
import com.java.transacoes_api.entities.Usuario;
import com.java.transacoes_api.repository.ContaRepository;
import com.java.transacoes_api.repository.MovimentacaoRepository;
import com.java.transacoes_api.repository.UsuarioRepository;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Value("${RESEND}")
    private String resendKey;


    @Transactional
    public Movimentacao transferencia(MovimentacaoInputDTO dto) throws Exception {
        var contaOrigem = contaRepository.findByNumeroConta(dto.contaOrigem())
                .orElseThrow(() -> new Exception("Conta de origem não encontrada"));

        var contaDestino = contaRepository.findByNumeroConta(dto.contaDestino())
                .orElseThrow(() -> new Exception("Conta de destino não encontrada"));

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

        Movimentacao movimentacaoDebito = new Movimentacao();
        movimentacaoDebito.setValor(dto.valor().negate());
        movimentacaoDebito.setDescricao("Transferência enviada para " + dto.contaDestino());
        movimentacaoDebito.setConta(contaOrigem);
        movimentacaoDebito.setData(LocalDateTime.now());
        movimentacaoDebito.setTipo(new TipoMovimentacao(1L, "Débito"));

        Movimentacao movimentacaoCredito = new Movimentacao();
        movimentacaoCredito.setValor(dto.valor());
        movimentacaoCredito.setDescricao("Transferência recebida de " + dto.contaOrigem());
        movimentacaoCredito.setConta(contaDestino);
        movimentacaoCredito.setData(LocalDateTime.now());
        movimentacaoCredito.setTipo(new TipoMovimentacao(2L, "Crédito"));

        movimentacaoRepository.save(movimentacaoDebito);
        movimentacaoRepository.save(movimentacaoCredito);

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

        Resend resend = new Resend(resendKey);

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


}
