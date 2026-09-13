package com.klu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_competencies")
public class UserCompetency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "competency_id")
    private Competency competency;

    private Integer currentLevel;

    // Default Constructor
    public UserCompetency() {
    }

    // Parameterized Constructor
    public UserCompetency(Long id, User user,
                          Competency competency,
                          Integer currentLevel) {
        this.id = id;
        this.user = user;
        this.competency = competency;
        this.currentLevel = currentLevel;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Competency getCompetency() {
        return competency;
    }

    public void setCompetency(Competency competency) {
        this.competency = competency;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }
}