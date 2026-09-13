package com.klu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.entity.QuizResult;

public interface QuizResultRepository 
        extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByUserId(Long userId);

    List<QuizResult> findByQuizId(Long quizId);

}