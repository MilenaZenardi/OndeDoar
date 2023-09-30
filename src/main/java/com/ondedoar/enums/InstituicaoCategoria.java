package com.ondedoar.enums;

import jakarta.persistence.Enumerated;

public enum InstituicaoCategoria {
    ANIMAL("animal"),
    HOSPITAL("hospital"),
    IDOSO("idoso"),
    CRIANÇA("criança"),
    ESCOLA("escola"),
    IGREJA("igreja"),
    OUTRO("outro");

    private final String descricao;

    InstituicaoCategoria(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }

}
