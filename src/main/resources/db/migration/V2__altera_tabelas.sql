ALTER TABLE Conta
    ADD CONSTRAINT fk_usuario_id
        FOREIGN KEY (usuario_id)
            REFERENCES Usuario(usuario_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE Movimentacao
    ADD CONSTRAINT fk_tipo_id
        FOREIGN KEY (tipo_id)
            REFERENCES TipoMovimentacao(tipo_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE Movimentacao
    ADD CONSTRAINT fk_conta_id
        FOREIGN KEY (conta_id)
            REFERENCES Conta(conta_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;
