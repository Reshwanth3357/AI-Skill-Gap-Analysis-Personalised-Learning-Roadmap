package com.klu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI aiLearningPlatformOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("AI Enabled Learning Platform API")
                        .description(
                                "REST APIs for AI Enabled Learning Platform "
                                + "including User Management, Competency Assessment, "
                                + "Learning Materials, Quizzes, Questions and Quiz Results."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("AI Learning Platform Team")
                                .email("support@ailearningplatform.com"))
                        .license(new License()
                                .name("API License")));
    }
}