package com.ondedoar.service;

import com.ondedoar.enums.InstituicaoCategoria;
import com.ondedoar.enums.InstituicaoStatus;
import com.ondedoar.model.InstituicaoModel;
import com.ondedoar.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    @Autowired
    InstituicaoRepository instituicaoRepository;

    public InstituicaoModel save(InstituicaoModel instituicaoModel) {
        instituicaoModel.setStatus(InstituicaoStatus.ANALISE);
        return instituicaoRepository.save(instituicaoModel);
    }

    public List<InstituicaoModel> getAll() {
        return instituicaoRepository.findAll();
    }

    public List<InstituicaoModel> getCategoria(InstituicaoCategoria categoria) {
        return instituicaoRepository.findByCategory(categoria);
    }

    public Optional<InstituicaoModel> getById(int id) {
        return instituicaoRepository.findById(id);
    }

    public InstituicaoModel update(InstituicaoModel instituicaoModel) {
        return instituicaoRepository.save(instituicaoModel);
    }
    public String delete(int id) {
            instituicaoRepository.deleteById(id);
        return "Deletado com sucesso";
    }
}
