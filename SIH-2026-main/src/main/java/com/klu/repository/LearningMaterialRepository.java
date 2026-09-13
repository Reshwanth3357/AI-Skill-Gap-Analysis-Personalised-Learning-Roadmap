package com.klu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.entity.LearningMaterial;

@Repository
public interface LearningMaterialRepository
        extends JpaRepository<LearningMaterial, Long> {

    List<LearningMaterial> findByUserId(Long userId);

    List<LearningMaterial> findBySkillNameIgnoreCase(String skillName);

}