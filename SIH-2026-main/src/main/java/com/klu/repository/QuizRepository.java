package com.klu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}