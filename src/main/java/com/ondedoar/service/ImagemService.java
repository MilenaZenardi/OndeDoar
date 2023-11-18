package com.ondedoar.service;

import com.ondedoar.model.ImagemModel;
import com.ondedoar.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagemService {
    @Autowired
    ImagemRepository imagemRepository;

    public ImagemModel save(ImagemModel imagemModel) {
        return imagemRepository.save(imagemModel);
    }


    public void delete(int id) {

    }
}
