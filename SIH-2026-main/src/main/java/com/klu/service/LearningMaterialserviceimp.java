package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.entity.LearningMaterial;
import com.klu.repository.LearningMaterialRepository;

@Service
public class LearningMaterialserviceimp
        implements LearningMaterialservice {

    @Autowired
    private LearningMaterialRepository repository;

    @Override
    public LearningMaterial addMaterial(
            LearningMaterial learningMaterial) {

        return repository.save(learningMaterial);
    }

    @Override
    public List<LearningMaterial> getAllMaterials() {

        return repository.findAll();
    }

    @Override
    public LearningMaterial getMaterialById(
            Long id) {

        return repository
                .findById(id)
                .orElse(null);
    }

    @Override
    public List<LearningMaterial> getMaterialsByUserId(
            Long userId) {

        return repository.findByUserId(userId);
    }

    @Override
    public void deleteMaterial(
            Long id) {

        repository.deleteById(id);
    }
}