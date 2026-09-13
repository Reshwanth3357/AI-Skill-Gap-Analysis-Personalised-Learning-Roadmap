package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.dto.QuizSubmission;
import com.klu.entity.QuizResult;
import com.klu.service.QuizResultservice;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class QuizResultController {

    @Autowired
    private QuizResultservice quizResultService;

    // Submit quiz and automatically calculate score
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuizResult(
            @RequestBody QuizSubmission submission) {

        QuizResult result =
                quizResultService.submitQuizResult(submission);

        if (result == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User or Quiz not found");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    // Get results by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizResult>> getUserResults(
            @PathVariable Long userId) {

        List<QuizResult> results =
                quizResultService.getUserResults(userId);

        return ResponseEntity.ok(results);
    }

    // Get results by quiz ID
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuizResult>> getQuizResults(
            @PathVariable Long quizId) {

        List<QuizResult> results =
                quizResultService.getQuizResults(quizId);

        return ResponseEntity.ok(results);
    }
}