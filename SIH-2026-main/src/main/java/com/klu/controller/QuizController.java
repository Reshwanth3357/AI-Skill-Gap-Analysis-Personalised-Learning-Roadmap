package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.entity.Question;
import com.klu.entity.Quiz;
import com.klu.repository.QuestionRepository;
import com.klu.service.Quizservice;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private Quizservice quizService;

    @Autowired
    private QuestionRepository questionRepository;


    // CREATE QUIZ
    @PostMapping
    public ResponseEntity<?> createQuiz(
            @RequestBody Quiz quiz) {

        Quiz savedQuiz =
                quizService.createQuiz(quiz);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedQuiz);
    }


    // GET QUIZ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(
            @PathVariable Long id) {

        Quiz quiz =
                quizService.getQuizById(id);

        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Quiz not found");
    }


    // GET QUESTIONS OF A QUIZ
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuizQuestions(
            @PathVariable Long quizId) {

        List<Question> questions =
                questionRepository.findByQuizId(quizId);

        return ResponseEntity.ok(questions);
    }


    // GET ALL QUIZZES OF A USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Quiz>> getUserQuizzes(
            @PathVariable Long userId) {

        List<Quiz> quizzes =
                quizService.getUserQuizzes(userId);

        return ResponseEntity.ok(quizzes);
    }
}