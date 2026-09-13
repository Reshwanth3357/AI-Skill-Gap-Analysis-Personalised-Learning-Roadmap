package com.klu.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klu.entity.Quiz;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIQuizController {


@Autowired
private AIQuizService aiQuizService;


// ==========================================
// USER CLICKS "COMPLETED"
// AI AUTOMATICALLY GENERATES 5 QUESTIONS
// ==========================================

@PostMapping("/completed/{materialId}")
public ResponseEntity<?> generateQuizAfterCompletion(

        @PathVariable Long materialId,

        @RequestParam Long userId) {

    try {

        // User does not enter number of questions
        // Backend automatically generates 5 questions

        Quiz quiz =
                aiQuizService.generateQuiz(
                        userId,
                        materialId,
                        5
                );

        return ResponseEntity.ok(quiz);

    } catch (Exception e) {

        return ResponseEntity
                .internalServerError()
                .body(
                        "Error generating AI quiz: "
                                + e.getMessage()
                );
    }
}


// ==========================================
// OPTIONAL: KEEP OLD API FOR MANUAL TESTING
// ==========================================

@PostMapping("/generate-quiz")
public ResponseEntity<?> generateQuiz(

        @RequestParam Long userId,

        @RequestParam Long materialId,

        @RequestParam int numberOfQuestions) {

    try {

        Quiz quiz =
                aiQuizService.generateQuiz(
                        userId,
                        materialId,
                        numberOfQuestions
                );

        return ResponseEntity.ok(quiz);

    } catch (Exception e) {

        return ResponseEntity
                .internalServerError()
                .body(e.getMessage());
    }
}


}
