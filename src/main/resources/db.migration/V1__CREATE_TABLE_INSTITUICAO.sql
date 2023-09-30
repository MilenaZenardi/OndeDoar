CREATE TABLE instituicao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(18) NOT NULL,
    razao_social VARCHAR(255),
    descricao VARCHAR(255) NOT NULL,
    responsavel VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    cep VARCHAR(10),
    categoria VARCHAR(255) NOT NULL,
    status VARCHAR(255)
);