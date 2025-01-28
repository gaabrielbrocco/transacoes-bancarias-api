CREATE TABLE TipoMovimentacao (
                                  tipo_id SERIAL PRIMARY KEY,
                                  nome VARCHAR(50) NOT NULL
);

CREATE TABLE Usuario (
                         usuario_id SERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Conta (
                       conta_id SERIAL PRIMARY KEY,
                       numero_conta VARCHAR(50) NOT NULL UNIQUE,
                       nome VARCHAR(255) NOT NULL,
                       saldo_inicial DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
                       data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       usuario_id INT NOT NULL,
                       FOREIGN KEY (usuario_id) REFERENCES Usuario(usuario_id)
);

CREATE TABLE Movimentacao (
                              id SERIAL PRIMARY KEY,
                              valor DECIMAL(15, 2) NOT NULL,
                              data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              descricao VARCHAR(255),
                              tipo_id INT NOT NULL,
                              conta_id INT NOT NULL,
                              FOREIGN KEY (tipo_id) REFERENCES TipoMovimentacao(tipo_id),
                              FOREIGN KEY (conta_id) REFERENCES Conta(conta_id)
);

INSERT INTO TipoMovimentacao (nome)
VALUES ('Débito'), ('Crédito');
