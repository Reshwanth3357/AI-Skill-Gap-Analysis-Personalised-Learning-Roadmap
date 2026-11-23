# AI-Skill-Gap-Analysis-Personalised-Learning-Roadmap
 Develop an AI enabled learning platform that identifies competency gaps, recommends personalized training through integration with the iGOT Karmayogi ecosystem, and capable of generating Quizzes and Multiple choice questions (MCQs) from uploaded learning materials to strengthen capacity building in India's Official Statistical System.​

# AI Skill Gap Analysis & Personalised Learning Roadmap

A Spring Boot backend platform (`AiLearningPlatformApplication`) built for AI-assisted quiz generation, learning recommendations, competency tracking, learning material management, and user/admin workflows.

## Architecture

![AI Skill Gap Analysis & Personalised Learning Roadmap Architecture](docs/architecture.png)
<img width="1024" height="572" alt="image" src="https://github.com/user-attachments/assets/c30cb745-f0f6-421f-894b-b9fe9c2cc758" />


## Key Modules & Features

* **AI Quiz & Recommendations (`com.klu.ai`)**: `AIQuizController`, `AIQuizService` / `AIQuizServiceImpl`, and `RecommendationController`, `RecommendationService` / `RecommendationServiceImpl`.
* **Competencies & Skill Tracking**: `CompetencyController`, `UserCompetencyController`, `Competency`, `UserCompetency`, and corresponding service/repository layers.
* **Quizzes, Questions & Results**: `QuizController`, `QuestionController`, `QuizResultController`, along with `Quiz`, `Question`, `QuizResult`, `QuizSubmission`, `GeneratedQuestion`, and `QuestionResponse`[cite: 6].
* **Learning Materials & Uploads**: `LearningMaterialController`, `LearningMaterial`, and sample PDFs in `SIH-2026-main/UPLOADS/` (`Machine_Learning_Introduction.pdf`, `Python_Data_Analysis (2).pdf`, `Statistics_Notes.pdf`, `python_learning_material.pdf`)[cite: 6].
* **User, Admin & Auth**: `UserController`, `AdminController`, `AuthController`, `PasswordController`, `User`, `Admin`, `Role`, and `EmailService` / `EmailServiceImpl`[cite: 6].
* **Security & API Docs**: JWT authentication (`JwtService`, `JwtAuthenticationFilter`, `SecurityConfig`), CORS/Web/RestTemplate/Email configuration (`CorsConfig`, `WebConfig`, `RestTemplateConfig`, `EmailConfig`), and Swagger/OpenAPI (`SwaggerConfig`)[cite: 6].

## Project Structure

```text
├── README.md
├── docs/
│   └── architecture.png
└── SIH-2026-main/
    ├── Dockerfile
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    ├── UPLOADS/
    └── src/
        └── main/
            ├── java/com/klu/
            │   ├── AiLearningPlatformApplication.java
            │   ├── ai/
            │   ├── config/
            │   ├── controller/
            │   ├── dto/
            │   ├── entity/
            │   ├── repository/
            │   ├── security/
            │   └── service/
            └── resources/
                └── application.properties

