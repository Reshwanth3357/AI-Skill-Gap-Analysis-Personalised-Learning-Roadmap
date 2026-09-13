package com.klu.service;

import java.util.List;

import com.klu.entity.LearningMaterial;

public interface LearningMaterialservice {

    // Add Learning Material
    LearningMaterial addMaterial(
            LearningMaterial learningMaterial);

    // Get Material By ID
    LearningMaterial getMaterialById(Long id);

    // Get Materials Uploaded By User
    List<LearningMaterial> getMaterialsByUserId(
            Long userId);

    // Get All Materials
    List<LearningMaterial> getAllMaterials();

    // Delete Material
    void deleteMaterial(Long id);
}