package com.klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);

}