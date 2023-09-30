package com.ondedoar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "imagem")
public class ImagemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;
    private String caminhoArquivo;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private InstituicaoModel instituicao;
}