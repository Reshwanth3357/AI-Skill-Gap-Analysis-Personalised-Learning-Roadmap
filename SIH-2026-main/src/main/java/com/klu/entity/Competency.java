package com.klu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "competencies")
public class Competency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    private Integer requiredLevel;

    private String description;

    // Default Constructor
    public Competency() {
    }

    // Parameterized Constructor
    public Competency(Long id, String skillName,
                      Integer requiredLevel, String description) {

        this.id = id;
        this.skillName = skillName;
        this.requiredLevel = requiredLevel;
        this.description = description;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}