package com.klu.service;

import java.util.List;

import com.klu.entity.UserCompetency;

public interface UserCompetencyservice {

    UserCompetency addUserCompetency(UserCompetency userCompetency);

    UserCompetency getUserCompetencyById(Long id);

    List<UserCompetency> getUserCompetencies(Long userId);

    List<UserCompetency> getAllUserCompetencies();

    UserCompetency updateUserCompetency(
            Long id,
            UserCompetency userCompetency);

    void deleteUserCompetency(Long id);

	List<UserCompetency> identifyCompetencyGaps(Long userId);
}
