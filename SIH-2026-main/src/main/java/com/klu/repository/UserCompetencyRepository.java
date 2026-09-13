package com.klu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.entity.UserCompetency;

@Repository
public interface UserCompetencyRepository
        extends JpaRepository<UserCompetency, Long> {

    List<UserCompetency> findByUserId(Long userId);
}