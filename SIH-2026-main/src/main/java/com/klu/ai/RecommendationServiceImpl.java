package com.klu.ai;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.entity.LearningMaterial;
import com.klu.entity.UserCompetency;
import com.klu.repository.LearningMaterialRepository;
import com.klu.repository.UserCompetencyRepository;

@Service
public class RecommendationServiceImpl
        implements RecommendationService {

    @Autowired
    private UserCompetencyRepository userCompetencyRepository;

    @Autowired
    private LearningMaterialRepository learningMaterialRepository;

    @Override
    public List<LearningMaterial> recommendMaterials(Long userId) {

        List<LearningMaterial> recommendedMaterials =
                new ArrayList<>();

        try {

            List<UserCompetency> userCompetencies =
                    userCompetencyRepository.findByUserId(userId);

            if (userCompetencies == null ||
                    userCompetencies.isEmpty()) {

                return recommendedMaterials;
            }

            for (UserCompetency userCompetency : userCompetencies) {

                if (userCompetency == null) {
                    continue;
                }

                if (userCompetency.getCompetency() == null) {
                    continue;
                }

                Integer currentLevel =
                        userCompetency.getCurrentLevel();

                Integer requiredLevel =
                        userCompetency.getCompetency()
                                .getRequiredLevel();

                if (currentLevel == null ||
                        requiredLevel == null) {
                    continue;
                }

                if (currentLevel < requiredLevel) {

                    String skillName =
                            userCompetency.getCompetency()
                                    .getSkillName();

                    if (skillName == null ||
                            skillName.isBlank()) {
                        continue;
                    }

                    List<LearningMaterial> materials =
                            learningMaterialRepository
                                    .findBySkillNameIgnoreCase(
                                            skillName
                                    );

                    if (materials != null &&
                            !materials.isEmpty()) {

                        recommendedMaterials.addAll(
                                materials
                        );
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return recommendedMaterials;
    }
}