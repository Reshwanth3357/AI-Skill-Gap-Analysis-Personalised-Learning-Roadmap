package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.entity.Competency;
import com.klu.repository.CompetencyRepository;

@Service
public class Competencyserviceimp implements Competencyservice {

    @Autowired
    private CompetencyRepository competencyRepository;

    @Override
    public Competency addCompetency(Competency competency) {
        return competencyRepository.save(competency);
    }

    @Override
    public Competency getCompetencyById(Long id) {
        return competencyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Competency> getAllCompetencies() {
        return competencyRepository.findAll();
    }

    @Override
    public Competency updateCompetency(Long id, Competency competency) {

        Competency existingCompetency =
                getCompetencyById(id);

        if (existingCompetency != null) {

            existingCompetency.setSkillName(
                    competency.getSkillName());

            existingCompetency.setRequiredLevel(
                    competency.getRequiredLevel());

            existingCompetency.setDescription(
                    competency.getDescription());

            return competencyRepository.save(
                    existingCompetency);
        }

        return null;
    }

    @Override
    public void deleteCompetency(Long id) {
        competencyRepository.deleteById(id);
    }
}