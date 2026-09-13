package com.klu.service;

import java.util.List;

import com.klu.dto.QuizSubmission;
import com.klu.entity.QuizResult;

public interface QuizResultservice {

    QuizResult submitQuizResult(QuizSubmission submission);

    List<QuizResult> getUserResults(Long userId);

    List<QuizResult> getQuizResults(Long quizId);
}