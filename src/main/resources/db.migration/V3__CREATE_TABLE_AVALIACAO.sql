CREATE TABLE avaliacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    comentario VARCHAR(255) NOT NULL,
    data DATE,
    instituicao_id INT NOT NULL,
    FOREIGN KEY (instituicao_id) REFERENCES instituicao (id)
);