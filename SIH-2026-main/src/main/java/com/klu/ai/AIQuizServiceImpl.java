package com.klu.ai;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.klu.entity.LearningMaterial;
import com.klu.entity.Question;
import com.klu.entity.Quiz;
import com.klu.entity.User;

import com.klu.repository.LearningMaterialRepository;
import com.klu.repository.QuestionRepository;
import com.klu.repository.QuizRepository;
import com.klu.repository.UserRepository;

@Service
public class AIQuizServiceImpl implements AIQuizService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LearningMaterialRepository learningMaterialRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${ollama.api.url}")
    private String ollamaApiUrl;

    @Value("${ollama.model}")
    private String model;


    @Override
    public Quiz generateQuiz(
            Long userId,
            Long materialId,
            int numberOfQuestions) {

        // Find User
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Find Learning Material
        LearningMaterial material =
                learningMaterialRepository
                        .findById(materialId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Learning material not found"
                                ));

        // Read material
        String materialContent =
                readMaterialContent(material);

        // Create Quiz
        Quiz quiz = new Quiz();

        quiz.setTitle(
                "AI Generated Quiz - "
                        + material.getFileName()
        );

        quiz.setUser(user);

        quiz.setLearningMaterial(material);

        // Save Quiz
        Quiz savedQuiz =
                quizRepository.save(quiz);

        // Generate Questions
        generateQuestions(
                savedQuiz,
                materialContent,
                numberOfQuestions
        );

        return savedQuiz;
    }


    // ==========================================
    // READ PDF OR TEXT FILE
    // ==========================================

    private String readMaterialContent(
            LearningMaterial material) {

        try {

            String filePath =
                    material.getFilePath();

            if (filePath == null ||
                    filePath.isEmpty()) {

                throw new RuntimeException(
                        "Material file path is missing"
                );
            }

            File file =
                    new File(filePath);

            if (!file.exists()) {

                throw new RuntimeException(
                        "Material file not found: "
                                + filePath
                );
            }


            // ==============================
            // READ PDF
            // ==============================

            if (material.getFileType() != null &&
                    material.getFileType()
                            .equalsIgnoreCase(
                                    "application/pdf"
                            )) {

                try (PDDocument document =
                             PDDocument.load(file)) {

                    PDFTextStripper pdfStripper =
                            new PDFTextStripper();

                    String pdfText =
                            pdfStripper.getText(document);

                    if (pdfText == null ||
                            pdfText.trim().isEmpty()) {

                        throw new RuntimeException(
                                "No text found in PDF"
                        );
                    }

                    return pdfText;
                }
            }


            // ==============================
            // READ NORMAL TEXT FILE
            // ==============================

            return Files.readString(
                    file.toPath()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error reading learning material: "
                            + e.getMessage(),
                    e
            );
        }
    }


    // ==========================================
    // GENERATE QUESTIONS USING OLLAMA
    // ==========================================

    private void generateQuestions(
            Quiz quiz,
            String materialContent,
            int numberOfQuestions) {

        try {

            // Limit material size
            if (materialContent.length() > 12000) {

                materialContent =
                        materialContent.substring(
                                0,
                                12000
                        );
            }


            // ======================================
            // PROMPT
            // ======================================

            String prompt = """

                    Generate exactly %d multiple choice questions
                    from the learning material below.

                    LEARNING MATERIAL:

                    %s

                    Return ONLY valid JSON.

                    Do not use markdown.
                    Do not use ```json.
                    Do not write any text before or after JSON.

                    Return this exact JSON structure:

                    {
                      "questions": [
                        {
                          "questionText": "Question here",
                          "optionA": "Option A",
                          "optionB": "Option B",
                          "optionC": "Option C",
                          "optionD": "Option D",
                          "correctAnswer": "A",
                          "explanation": "Explanation here"
                        }
                      ]
                    }

                    IMPORTANT:

                    1. Generate exactly %d questions.
                    2. correctAnswer must be only A, B, C, or D.
                    3. Generate questions ONLY from the provided material.
                    4. Every question must have four options.
                    5. Return valid JSON only.

                    """

                    .formatted(
                            numberOfQuestions,
                            materialContent,
                            numberOfQuestions
                    );


            // ======================================
            // OLLAMA REQUEST BODY
            // ======================================

            Map<String, Object> requestBody =
                    Map.of(
                            "model",
                            model,

                            "prompt",
                            prompt,

                            "stream",
                            false,

                            "format",
                            "json"
                    );


            // ======================================
            // HEADERS
            // ======================================

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );


            // ======================================
            // HTTP REQUEST
            // ======================================

            HttpEntity<Map<String, Object>>
                    request =
                    new HttpEntity<>(
                            requestBody,
                            headers
                    );


            RestTemplate restTemplate =
                    new RestTemplate();


            // ======================================
            // CALL OLLAMA
            // ======================================

            String response =
                    restTemplate.postForObject(
                            ollamaApiUrl,
                            request,
                            String.class
                    );


            if (response == null ||
                    response.isEmpty()) {

                throw new RuntimeException(
                        "No response received from Ollama"
                );
            }


            // ======================================
            // PARSE OLLAMA API RESPONSE
            // ======================================

            JsonNode root =
                    objectMapper.readTree(response);


            String aiContent =
                    root.path("response")
                            .asText();


            if (aiContent == null ||
                    aiContent.trim().isEmpty()) {

                throw new RuntimeException(
                        "Ollama returned empty content"
                );
            }


            System.out.println(
                    "OLLAMA RESPONSE: "
                            + aiContent
            );


            // Remove markdown if present
            aiContent =
                    aiContent
                            .replace("```json", "")
                            .replace("```", "")
                            .trim();


            // ======================================
            // PARSE GENERATED JSON
            // ======================================

            JsonNode generatedJson =
                    objectMapper.readTree(
                            aiContent
                    );


            JsonNode questions;


            // Case 1:
            // {
            //   "questions": [...]
            // }

            if (generatedJson.isObject() &&
                    generatedJson.has("questions")) {

                questions =
                        generatedJson.get("questions");
            }


            // Case 2:
            // [...]

            else if (generatedJson.isArray()) {

                questions =
                        generatedJson;
            }


            else {

                throw new RuntimeException(
                        "Invalid question format from Ollama"
                );
            }


            if (!questions.isArray()) {

                throw new RuntimeException(
                        "Questions field is not an array"
                );
            }


            // ======================================
            // SAVE QUESTIONS
            // ======================================

            int savedCount = 0;


            for (JsonNode node : questions) {

                if (savedCount >=
                        numberOfQuestions) {

                    break;
                }


                String questionText =
                        node.path(
                                "questionText"
                        ).asText();

                String optionA =
                        node.path(
                                "optionA"
                        ).asText();

                String optionB =
                        node.path(
                                "optionB"
                        ).asText();

                String optionC =
                        node.path(
                                "optionC"
                        ).asText();

                String optionD =
                        node.path(
                                "optionD"
                        ).asText();

                String correctAnswer =
                        node.path(
                                "correctAnswer"
                        ).asText();

                String explanation =
                        node.path(
                                "explanation"
                        ).asText();


                // Validate question

                if (questionText.isEmpty() ||
                        optionA.isEmpty() ||
                        optionB.isEmpty() ||
                        optionC.isEmpty() ||
                        optionD.isEmpty()) {

                    continue;
                }


                Question question =
                        new Question();


                question.setQuestionText(
                        questionText
                );

                question.setOptionA(
                        optionA
                );

                question.setOptionB(
                        optionB
                );

                question.setOptionC(
                        optionC
                );

                question.setOptionD(
                        optionD
                );

                question.setCorrectAnswer(
                        correctAnswer
                                .trim()
                                .toUpperCase()
                );

                question.setExplanation(
                        explanation
                );


                // Connect Question to Quiz

                question.setQuiz(quiz);


                // Save Question

                questionRepository.save(
                        question
                );


                savedCount++;
            }


            if (savedCount == 0) {

                throw new RuntimeException(
                        "Ollama generated zero valid questions"
                );
            }


            System.out.println(
                    "Successfully saved "
                            + savedCount
                            + " questions"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Error generating AI quiz: "
                            + e.getMessage(),
                    e
            );
        }
    }
}