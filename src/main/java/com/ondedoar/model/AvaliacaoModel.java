package com.ondedoar.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "avaliacao")
public class AvaliacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String comentario;
    @Column
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private InstituicaoModel instituicao;
}
