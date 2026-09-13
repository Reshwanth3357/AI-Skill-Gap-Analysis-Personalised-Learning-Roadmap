package com.klu.service;

import java.util.List;

import com.klu.entity.User;

public interface Userservice {

    User addUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User login(String email, String password);

    User registerUser(User user);
}