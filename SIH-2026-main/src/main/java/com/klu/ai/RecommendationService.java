package com.klu.ai;

import java.util.List;

import com.klu.entity.LearningMaterial;

public interface RecommendationService {

    List<LearningMaterial> recommendMaterials(Long userId);

}