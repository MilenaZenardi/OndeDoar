package com.ondedoar.repository;

import com.ondedoar.enums.InstituicaoCategoria;
import com.ondedoar.model.InstituicaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituicaoRepository extends JpaRepository<InstituicaoModel, Integer> {
    @Query("SELECT i FROM InstituicaoModel i WHERE i.categoria = :categoria")
    List<InstituicaoModel> findByCategory(InstituicaoCategoria categoria);

    @Query("SELECT i FROM InstituicaoModel i WHERE i.status = 'ATIVO'")
    List<InstituicaoModel> findAllActives();

    @Query("SELECT i FROM InstituicaoModel i WHERE i.status = 'ANALISE'")
    List<InstituicaoModel> findAllAnalyzes();
}
