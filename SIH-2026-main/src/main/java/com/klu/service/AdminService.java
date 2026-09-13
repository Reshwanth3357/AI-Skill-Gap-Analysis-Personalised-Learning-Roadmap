package com.klu.service;

import java.util.List;

import com.klu.entity.Admin;

public interface AdminService {

    Admin registerAdmin(Admin admin);

    Admin login(String email, String password);

    Admin findByEmail(String email);

    List<Admin> getAllAdmins();

    Admin getAdminById(Long id);

    void deleteAdmin(Long id);
}