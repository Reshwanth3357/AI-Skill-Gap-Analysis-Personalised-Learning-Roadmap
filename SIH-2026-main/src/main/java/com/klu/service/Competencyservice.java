package com.klu.service;

import java.util.List;

import com.klu.entity.Competency;

public interface Competencyservice {

    Competency addCompetency(Competency competency);

    Competency getCompetencyById(Long id);

    List<Competency> getAllCompetencies();

    Competency updateCompetency(Long id, Competency competency);

    void deleteCompetency(Long id);
}