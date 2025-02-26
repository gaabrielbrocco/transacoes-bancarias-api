package cgi.log.watcher.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "mensagem", nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Column(name = "caminho", nullable = false, columnDefinition = "TEXT")
    private String caminho;

    @Column(name = "stack", nullable = false, columnDefinition = "TEXT")
    private String stack;

    @Column(name = "data_ocorrencia")
    private LocalDateTime dataOcorrencia = LocalDateTime.now();

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    public Log(Long id, String tipo, String mensagem, String url, String caminho, String stack, String userAgent, LocalDateTime dataOcorrencia) {
        this.id = id;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.url = url;
        this.caminho = caminho;
        this.stack = stack;
        this.userAgent = userAgent;
        this.dataOcorrencia = dataOcorrencia;
    }

    public Log(String tipo, String mensagem, String url, String caminho, String stack, String userAgent) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.url = url;
        this.caminho = caminho;
        this.stack = stack;
        this.userAgent = userAgent;
    }

    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

}
