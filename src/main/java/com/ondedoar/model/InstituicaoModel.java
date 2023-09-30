package com.ondedoar.model;

import com.ondedoar.enums.InstituicaoCategoria;
import com.ondedoar.enums.InstituicaoStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "instituicao")
public class InstituicaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CNPJ
    private String cnpj;
    @Column
    private String razaoSocial;
    @Column
    private String descricao;
    @Column
    private String responsavel;
    @Column
    private String endereco;
    @Column
    private String cep;
    @ElementCollection
    @CollectionTable(name = "telefone", joinColumns = @JoinColumn(name = "instituicao_id"))
    @Column(name = "numero")
    private List<String> telefones;
    @Column
    @Enumerated(EnumType.STRING)
    private InstituicaoCategoria categoria;
    @Column
    @Enumerated(EnumType.STRING)
    private InstituicaoStatus status;
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<AvaliacaoModel> avaliacoes;
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<ImagemModel> imagens;
}
