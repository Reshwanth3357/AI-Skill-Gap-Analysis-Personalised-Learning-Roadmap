package com.klu.ai;

import com.klu.entity.Quiz;

public interface AIQuizService {


Quiz generateQuiz(
        Long userId,
        Long materialId,
        int numberOfQuestions
);


}
