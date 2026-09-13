package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.entity.Competency;
import com.klu.service.Competencyservice;

@RestController
@RequestMapping("/api/competencies")

public class CompetencyController {

    @Autowired
    private Competencyservice competencyService;

    // Add Competency
    @PostMapping
    public ResponseEntity<?> addCompetency(
            @RequestBody Competency competency) {

        Competency savedCompetency =
                competencyService.addCompetency(competency);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCompetency);
    }

    // Get All Competencies
    @GetMapping
    public ResponseEntity<List<Competency>> getAllCompetencies() {

        List<Competency> competencies =
                competencyService.getAllCompetencies();

        return ResponseEntity.ok(competencies);
    }
}