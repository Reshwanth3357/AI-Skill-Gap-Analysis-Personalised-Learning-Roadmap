
package com.klu.service;

import java.util.List;

import com.klu.entity.Quiz;

public interface Quizservice {

    // Create a quiz
    Quiz createQuiz(Quiz quiz);

    // Get quiz by ID
    Quiz getQuizById(Long id);

    // Get quizzes for a user
    List<Quiz> getUserQuizzes(Long userId);
}