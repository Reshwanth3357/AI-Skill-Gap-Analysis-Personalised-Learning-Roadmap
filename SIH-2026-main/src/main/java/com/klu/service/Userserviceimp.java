package com.klu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klu.entity.Role;
import com.klu.entity.User;
import com.klu.repository.UserRepository;

@Service
public class Userserviceimp implements Userservice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${login.max-attempts}")
    private int maxAttempts;

    @Value("${login.lock-duration-minutes}")
    private int lockDurationMinutes;

    @Override
    public User addUser(User user) {

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
        user.setEmailVerified(false);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = getUserById(id);

        if (existingUser == null) {
            return null;
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setDesignation(user.getDesignation());
        existingUser.setPhone(user.getPhone());

        if (user.getPassword() != null
                && !user.getPassword().isEmpty()) {

            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        // Check whether account is locked
        if (user.isAccountLocked()) {

            LocalDateTime unlockTime =
                    user.getLockTime()
                            .plusMinutes(lockDurationMinutes);

            // Automatically unlock account
            if (LocalDateTime.now().isAfter(unlockTime)) {

                user.setAccountLocked(false);
                user.setFailedLoginAttempts(0);
                user.setLockTime(null);

                userRepository.save(user);

            } else {

                return null;
            }
        }

        // Check password
        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            int attempts =
                    user.getFailedLoginAttempts() + 1;

            user.setFailedLoginAttempts(attempts);

            // Lock account
            if (attempts >= maxAttempts) {

                user.setAccountLocked(true);

                user.setLockTime(
                        LocalDateTime.now()
                );
            }

            userRepository.save(user);

            return null;
        }

        // Successful login

        user.setFailedLoginAttempts(0);

        user.setAccountLocked(false);

        user.setLockTime(null);

        user.setLastLogin(
                LocalDateTime.now()
        );

        userRepository.save(user);

        return user;
    }

    @Override
    public User registerUser(User user) {

        User existingUser =
                userRepository.findByEmail(
                        user.getEmail()
                );

        if (existingUser != null) {
            return null;
        }

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        user.setFailedLoginAttempts(0);

        user.setAccountLocked(false);

        user.setEmailVerified(false);

        return userRepository.save(user);
    }
}