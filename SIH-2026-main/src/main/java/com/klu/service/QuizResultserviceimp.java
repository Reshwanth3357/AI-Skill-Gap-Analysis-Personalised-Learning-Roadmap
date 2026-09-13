package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.dto.QuizSubmission;
import com.klu.entity.Question;
import com.klu.entity.Quiz;
import com.klu.entity.QuizResult;
import com.klu.entity.User;
import com.klu.repository.QuestionRepository;
import com.klu.repository.QuizRepository;
import com.klu.repository.QuizResultRepository;
import com.klu.repository.UserRepository;

@Service
public class QuizResultserviceimp implements QuizResultservice {

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public QuizResult submitQuizResult(QuizSubmission submission) {

        // Get user
        User user = userRepository
                .findById(submission.getUserId())
                .orElse(null);

        // Get quiz
        Quiz quiz = quizRepository
                .findById(submission.getQuizId())
                .orElse(null);

        // Check whether user and quiz exist
        if (user == null || quiz == null) {
            return null;
        }

        // Get all questions for this quiz
        List<Question> questions =
                questionRepository.findByQuizId(
                        submission.getQuizId());

        int score = 0;

        // Compare selected answers with correct answers
        for (Question question : questions) {

            String selectedAnswer =
                    submission.getAnswers()
                            .get(question.getId());

            if (selectedAnswer != null
                    && selectedAnswer.equalsIgnoreCase(
                            question.getCorrectAnswer())) {

                score++;
            }
        }

        // Create result
        QuizResult quizResult = new QuizResult();

        quizResult.setScore(score);
        quizResult.setTotalQuestions(questions.size());
        quizResult.setUser(user);
        quizResult.setQuiz(quiz);

        // Save result in database
        return quizResultRepository.save(quizResult);
    }

    @Override
    public List<QuizResult> getUserResults(Long userId) {

        return quizResultRepository.findByUserId(userId);
    }

    @Override
    public List<QuizResult> getQuizResults(Long quizId) {

        return quizResultRepository.findByQuizId(quizId);
    }
}