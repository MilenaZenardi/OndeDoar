CREATE TABLE users(
    id int PRIMARY KEY UNIQUE,
    nome TEXT NOT NULL,
    login TEXT NOT NULL,
    password TEXT not null ,
    email TEXT not null ,
    role TEXT not null,
    cpf TEXT not null
);