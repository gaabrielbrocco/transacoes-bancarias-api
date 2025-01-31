package com.java.transacoes_api.movimentacao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tipomovimentacao")
public class TipoMovimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    public TipoMovimentacao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public TipoMovimentacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
