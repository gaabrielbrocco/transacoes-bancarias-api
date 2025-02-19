package com.java.transacoes_api.conta.entities;

import com.java.transacoes_api.usuario.entities.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conta")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conta_id")
    private Long id;

    @Column(name = "numero_conta", nullable = false, unique = true)
    private String numeroConta;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Conta(Long id, String numeroConta, String nome, BigDecimal saldo, LocalDateTime dataCriacao, Usuario usuario) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
    }

    public Conta(String numeroConta, String nome, BigDecimal saldo, LocalDateTime dataCriacao, Usuario usuario) {
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
    }

    public Conta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
