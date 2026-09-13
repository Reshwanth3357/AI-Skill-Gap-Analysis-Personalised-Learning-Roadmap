package com.klu.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({
            "password"
    })
    private User user;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @JsonIgnoreProperties({
            "user"
    })
    private LearningMaterial learningMaterial;

    public Quiz() {
    }

    public Quiz(Long id, String title, LocalDateTime createdAt,
                User user, LearningMaterial learningMaterial) {

        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.user = user;
        this.learningMaterial = learningMaterial;
    }

    @PrePersist
    public void setCreatedAt() {

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LearningMaterial getLearningMaterial() {
        return learningMaterial;
    }

    public void setLearningMaterial(
            LearningMaterial learningMaterial) {

        this.learningMaterial = learningMaterial;
    }
}