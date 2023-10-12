package com.ondedoar.dto;

import com.ondedoar.enums.InstituicaoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public record InstituicaoRecordDto(@CNPJ(message = "CNPJ inválido") String cnpj,
                                   String razaoSocial,
                                   @NotBlank(message = "Descrição é obrigatória") String descricao,
                                   @NotBlank(message = "Responsável é obrigatório") String responsavel,
                                   String endereco, String cep,
                                   List<String> telefones,
                                   @NotNull(message = "A categoria é obrigatória") InstituicaoCategoria categoria) {

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
