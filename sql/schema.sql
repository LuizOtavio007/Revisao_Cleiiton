CREATE TABLE IF NOT EXISTS pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    idade INTEGER NOT NULL CHECK (idade > 0),
    email VARCHAR(150),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('CLIENTE', 'FORNECEDOR', 'FUNCIONARIO', 'PESSOA')),
    salario NUMERIC(12,2) CHECK (salario IS NULL OR salario >= 0),
    setor VARCHAR(30),
    empresa VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS caixa_da_agua (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(80) NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    dimensao DOUBLE PRECISION[] NOT NULL,
    cor VARCHAR(30) NOT NULL,
    material VARCHAR(30) NOT NULL,
    formato VARCHAR(60) NOT NULL,
    preco NUMERIC(12,2) NOT NULL CHECK (preco > 0),
    quantidade_estoque INTEGER NOT NULL DEFAULT 0 CHECK (quantidade_estoque >= 0)
);

CREATE TABLE IF NOT EXISTS venda (
    id SERIAL PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL,
    pagador VARCHAR(120) NOT NULL,
    recebedor VARCHAR(120) NOT NULL,
    responsavel_id INTEGER NOT NULL REFERENCES pessoa(id),
    valor_total NUMERIC(12,2) NOT NULL CHECK (valor_total > 0)
);

-- Relação N:N entre venda e produto, resolvida pela tabela associativa item_venda.
CREATE TABLE IF NOT EXISTS item_venda (
    venda_id INTEGER NOT NULL REFERENCES venda(id) ON DELETE CASCADE,
    produto_id INTEGER NOT NULL REFERENCES caixa_da_agua(id),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    valor_unitario NUMERIC(12,2) NOT NULL CHECK (valor_unitario > 0),
    PRIMARY KEY (venda_id, produto_id)
);

CREATE TABLE IF NOT EXISTS movimentacao (
    id SERIAL PRIMARY KEY,
    valor NUMERIC(12,2) NOT NULL CHECK (valor > 0),
    data_hora TIMESTAMP NOT NULL,
    descricao VARCHAR(60) NOT NULL,
    pagador VARCHAR(120) NOT NULL,
    recebedor VARCHAR(120) NOT NULL,
    responsavel_id INTEGER NOT NULL REFERENCES pessoa(id)
);
