package com.ondedoar.dto;

import com.ondedoar.enums.UserRole;

public record RegisterDTO(String nome, String login, String password, String email, UserRole role, String cpf) {

}
