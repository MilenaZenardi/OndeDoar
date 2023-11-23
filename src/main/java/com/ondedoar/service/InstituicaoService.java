package com.ondedoar.service;

import com.ondedoar.enums.InstituicaoCategoria;
import com.ondedoar.enums.InstituicaoStatus;
import com.ondedoar.model.InstituicaoModel;
import com.ondedoar.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {

    @Autowired
    InstituicaoRepository instituicaoRepository;

    public InstituicaoModel save(InstituicaoModel instituicaoModel) {
        return instituicaoRepository.save(instituicaoModel);
    }

    public List<InstituicaoModel> getAllActives() {
        return instituicaoRepository.findAllActives();
    }

    public List<InstituicaoModel> getAllAnalizes() {
        return instituicaoRepository.findAllAnalyzes();
    }


    public List<InstituicaoModel> getCategoria(InstituicaoCategoria categoria) {
        return instituicaoRepository.findByCategory(categoria);
    }

    public InstituicaoModel getById(int id) {
        InstituicaoModel instituicaoModel =  instituicaoRepository.getById(id);

        return instituicaoModel;
    }

    public void delete(Integer id) {
        this.instituicaoRepository.deleteById(id);
    }


}
