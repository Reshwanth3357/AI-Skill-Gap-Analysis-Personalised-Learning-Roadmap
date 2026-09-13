package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.dto.QuestionResponse;
import com.klu.entity.Question;
import com.klu.repository.QuestionRepository;

@Service
public class Questionserviceimp implements Questionservice {

    @Autowired
    private QuestionRepository questionRepository;

    // Add Question
    @Override
    public Question addQuestion(Question question) {

        return questionRepository.save(question);
    }

    // Get Question By ID
    @Override
    public Question getQuestionById(Long id) {

        return questionRepository
                .findById(id)
                .orElse(null);
    }

    // Get Questions By Quiz ID
    // Returns only safe data to the user
    @Override
    public List<QuestionResponse> getQuestionsByQuizId(Long quizId) {

        List<Question> questions =
                questionRepository.findByQuizId(quizId);

        return questions.stream()
                .map(question -> new QuestionResponse(

                        question.getId(),

                        question.getQuestionText(),

                        question.getOptionA(),

                        question.getOptionB(),

                        question.getOptionC(),

                        question.getOptionD()
                ))
                .toList();
    }

    // Get All Questions
    @Override
    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }
}