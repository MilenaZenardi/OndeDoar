CREATE TABLE imagem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomeArquivo VARCHAR(255),
    caminhoArquivo VARCHAR(255),
    instituicao_id INT,
    FOREIGN KEY (instituicao_id) REFERENCES instituicao(id)
);