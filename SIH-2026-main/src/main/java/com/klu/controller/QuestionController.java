package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.dto.QuestionResponse;
import com.klu.entity.Question;
import com.klu.service.Questionservice;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private Questionservice questionService;


    // Add Question
    @PostMapping
    public ResponseEntity<?> addQuestion(
            @RequestBody Question question) {

        Question savedQuestion =
                questionService.addQuestion(question);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedQuestion);
    }


    // Get Questions By Quiz ID
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByQuiz(
            @PathVariable Long quizId) {

        List<QuestionResponse> questions =
                questionService
                        .getQuestionsByQuizId(quizId);

        return ResponseEntity.ok(questions);
    }
}