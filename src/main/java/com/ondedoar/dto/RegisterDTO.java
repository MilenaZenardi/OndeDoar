package com.ondedoar.dto;

import org.hibernate.validator.constraints.NotBlank;

public record RegisterDTO(@NotBlank(message = "Nome é obrigatório") String nome,
                          @NotBlank(message = "Login é obrigatório") String login,
                          @NotBlank(message = "Senha é obrigatória") String password,
                          @NotBlank(message = "E-mail é obrigatório") String email,
                          @NotBlank(message = "CPF inválido") String cpf) {

}
