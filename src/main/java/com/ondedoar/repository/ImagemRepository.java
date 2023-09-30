package com.ondedoar.repository;

import com.ondedoar.model.ImagemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemModel, Integer> {
}
