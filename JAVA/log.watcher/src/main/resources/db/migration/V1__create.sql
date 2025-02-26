CREATE TABLE logs (
                      id SERIAL PRIMARY KEY,
                      tipo VARCHAR(50) NOT NULL,
                      mensagem TEXT NOT NULL,
                      url TEXT,
                      data_ocorrencia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      user_agent TEXT,
                      detalhes JSONB
);