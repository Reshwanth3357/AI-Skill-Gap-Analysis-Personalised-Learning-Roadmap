package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.entity.Admin;
import com.klu.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public Admin registerAdmin(Admin admin) {

        return adminRepository.save(admin);

    }


    @Override
    public Admin login(
            String email,
            String password) {

        Admin admin =
                adminRepository.findByEmail(email);

        if (admin != null &&
                admin.getPassword().equals(password)) {

            return admin;
        }

        return null;
    }


    @Override
    public Admin findByEmail(String email) {

        return adminRepository.findByEmail(email);

    }


    @Override
    public List<Admin> getAllAdmins() {

        return adminRepository.findAll();

    }


    @Override
    public Admin getAdminById(Long id) {

        return adminRepository
                .findById(id)
                .orElse(null);

    }


    @Override
    public void deleteAdmin(Long id) {

        adminRepository.deleteById(id);

    }
}