package com.klu.service;

import java.util.List;

import com.klu.dto.QuestionResponse;
import com.klu.entity.Question;

public interface Questionservice {

    // Add Question
    Question addQuestion(Question question);

    // Get Question By ID
    Question getQuestionById(Long id);

    // Get Questions By Quiz ID
    // This hides correctAnswer and explanation
    List<QuestionResponse> getQuestionsByQuizId(Long quizId);

    // Get All Questions
    List<Question> getAllQuestions();
}