ALTER TABLE Conta
DROP CONSTRAINT fk_usuario_id;

ALTER TABLE Conta
    ADD CONSTRAINT fk_usuario_id
        FOREIGN KEY (usuario_id)
            REFERENCES Usuario(usuario_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE;
