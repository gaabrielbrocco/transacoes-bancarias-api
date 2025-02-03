package com.java.transacoes_api.blockchain;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Block {
    private String hash;
    private String previousHash;
    private LocalDateTime timestamp;
    private Map data;
    private String tipoMovimentacao;

    public Block(String previousHash, Map data, String tipoMovimentacao) {
        this.previousHash = previousHash;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.tipoMovimentacao = tipoMovimentacao;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        StringBuilder input = new StringBuilder();
        input.append(previousHash);
        input.append(timestamp.toString());
        input.append(data);
        input.append(tipoMovimentacao);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.toString().getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao calcular o hash", e);
        }
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map getData() {
        return data;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }
}
