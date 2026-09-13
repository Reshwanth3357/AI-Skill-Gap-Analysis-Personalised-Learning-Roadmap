package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.entity.UserCompetency;
import com.klu.service.UserCompetencyservice;

@RestController
@RequestMapping("/api/usercompetencies")
@CrossOrigin(origins = "*")
public class UserCompetencyController {

    @Autowired
    private UserCompetencyservice userCompetencyService;

    // Assess User Competency
    @PostMapping("/assess")
    public ResponseEntity<?> assessCompetency(
            @RequestBody UserCompetency userCompetency) {

        UserCompetency result =
                userCompetencyService.addUserCompetency(userCompetency);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    // Get User Competencies
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCompetency>> getUserCompetencies(
            @PathVariable Long userId) {

        List<UserCompetency> competencies =
                userCompetencyService
                        .getUserCompetencies(userId);

        return ResponseEntity.ok(competencies);
    }

    // Identify Competency Gaps
    @GetMapping("/gaps/{userId}")
    public ResponseEntity<?> identifyCompetencyGaps(
            @PathVariable Long userId) {

        List<UserCompetency> gaps =
                userCompetencyService
                        .identifyCompetencyGaps(userId);

        return ResponseEntity.ok(gaps);
    }
}