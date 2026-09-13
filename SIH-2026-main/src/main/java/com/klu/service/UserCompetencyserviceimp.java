package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.entity.UserCompetency;
import com.klu.repository.UserCompetencyRepository;

@Service
public class UserCompetencyserviceimp
        implements UserCompetencyservice {

    @Autowired
    private UserCompetencyRepository userCompetencyRepository;

    @Override
    public UserCompetency addUserCompetency(
            UserCompetency userCompetency) {

        return userCompetencyRepository.save(userCompetency);
    }

    @Override
    public UserCompetency getUserCompetencyById(Long id) {

        return userCompetencyRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public List<UserCompetency> getUserCompetencies(Long userId) {

        return userCompetencyRepository.findByUserId(userId);
    }

    @Override
    public List<UserCompetency> getAllUserCompetencies() {

        return userCompetencyRepository.findAll();
    }

    @Override
    public UserCompetency updateUserCompetency(
            Long id,
            UserCompetency userCompetency) {

        UserCompetency existingUserCompetency =
                getUserCompetencyById(id);

        if (existingUserCompetency != null) {

            existingUserCompetency.setCurrentLevel(
                    userCompetency.getCurrentLevel());

            return userCompetencyRepository.save(
                    existingUserCompetency);
        }

        return null;
    }

    @Override
    public void deleteUserCompetency(Long id) {

        userCompetencyRepository.deleteById(id);
    }

    // Identify Competency Gaps
    @Override
    public List<UserCompetency> identifyCompetencyGaps(Long userId) {

        List<UserCompetency> userCompetencies =
                userCompetencyRepository.findByUserId(userId);

        return userCompetencies.stream()
                .filter(userCompetency ->
                        userCompetency.getCurrentLevel() <
                        userCompetency.getCompetency().getRequiredLevel())
                .toList();
    }
}