package com.ondedoar.dto;

import com.ondedoar.enums.InstituicaoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InstituicaoRecordDto(@NotBlank String cnpj, String razaoSocial, @NotBlank String descricao,
                                   @NotBlank String responsavel,
                                   String endereco, String cep, List<String> telefones,
                                   @NotNull InstituicaoCategoria categoria) {

    @Override
    public String cnpj() {
        return cnpj;
    }

    @Override
    public String razaoSocial() {
        return razaoSocial;
    }

    @Override
    public String descricao() {
        return descricao;
    }

    @Override
    public String responsavel() {
        return responsavel;
    }

    @Override
    public String endereco() {
        return endereco;
    }

    @Override
    public String cep() {
        return cep;
    }

    @Override
    public List<String> telefones() {
        return telefones;
    }

    @Override
    public InstituicaoCategoria categoria() {
        return categoria;
    }
}
