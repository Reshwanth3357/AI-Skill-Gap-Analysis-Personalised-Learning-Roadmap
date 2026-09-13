package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.entity.LearningMaterial;
import com.klu.entity.Quiz;
import com.klu.entity.User;
import com.klu.repository.LearningMaterialRepository;
import com.klu.repository.QuizRepository;
import com.klu.repository.UserRepository;

@Service
public class Quizserviceimp implements Quizservice {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LearningMaterialRepository learningMaterialRepository;

    @Override
    public Quiz createQuiz(Quiz quiz) {

        // Get the actual user from database
        User user = userRepository
                .findById(quiz.getUser().getId())
                .orElse(null);

        // Get the actual learning material from database
        LearningMaterial learningMaterial =
                learningMaterialRepository
                .findById(quiz.getLearningMaterial().getId())
                .orElse(null);

        // Check whether user or material exists
        if (user == null || learningMaterial == null) {
            return null;
        }

        // Set actual database objects
        quiz.setUser(user);
        quiz.setLearningMaterial(learningMaterial);

        // Save quiz
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuizById(Long id) {

        return quizRepository.findById(id).orElse(null);
    }

    @Override
    public List<Quiz> getUserQuizzes(Long userId) {

        return quizRepository.findByUserId(userId);
    }
}