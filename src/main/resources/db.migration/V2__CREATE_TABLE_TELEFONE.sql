CREATE TABLE telefone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    instituicao_id INT NOT NULL,
    FOREIGN KEY (instituicao_id) REFERENCES instituicao(id)
);