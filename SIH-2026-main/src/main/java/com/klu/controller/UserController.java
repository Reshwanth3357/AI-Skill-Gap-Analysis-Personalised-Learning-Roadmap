package com.klu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.dto.UserResponse;
import com.klu.entity.User;
import com.klu.security.JwtService;
import com.klu.service.EmailService;
import com.klu.service.Userservice;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private Userservice userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;


    // Convert User Entity to UserResponse

    private UserResponse convertToResponse(User user) {

        return new UserResponse(

                user.getId(),

                user.getName(),

                user.getEmail(),

                user.getDesignation()

        );
    }


    // USER REGISTRATION

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody User user) {

        User registeredUser =
                userService.registerUser(user);

        if (registeredUser != null) {

            // Send Welcome Email

            String subject =
                    "Welcome to AI Learning Platform";

            String body =
                    "Hello " + registeredUser.getName() + ",\n\n"
                    + "Welcome to AI Learning Platform!\n\n"
                    + "Your account has been created successfully.\n\n"
                    + "You can now login and start learning.\n\n"
                    + "Regards,\n"
                    + "AI Learning Platform Team";

            try {

                emailService.sendEmail(

                        registeredUser.getEmail(),

                        subject,

                        body

                );

            } catch (Exception e) {

                System.out.println(
                        "Email sending failed: "
                        + e.getMessage()
                );

            }

            return ResponseEntity

                    .status(HttpStatus.CREATED)

                    .body(
                            convertToResponse(
                                    registeredUser
                            )
                    );
        }

        return ResponseEntity

                .status(HttpStatus.CONFLICT)

                .body("Email already registered");
    }


    // USER LOGIN WITH JWT

    @PostMapping("/login")
    public ResponseEntity<?> login(

            @RequestParam String email,

            @RequestParam String password) {

        User user =
                userService.login(email, password);

        if (user != null) {

            String token =
                    jwtService.generateToken(

                            user.getEmail(),

                            user.getRole().name()

                    );

            Map<String, Object> response =
                    new HashMap<>();

            response.put("token", token);

            response.put(
                    "user",
                    convertToResponse(user)
            );

            response.put(
                    "role",
                    user.getRole()
            );

            response.put(
                    "lastLogin",
                    user.getLastLogin()
            );

            response.put(
                    "message",
                    "Login successful"
            );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity

                .status(HttpStatus.UNAUTHORIZED)

                .body(
                        "Invalid email or password. "
                        + "Account may be temporarily locked."
                );
    }


    // GET USER BY ID

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(

            @PathVariable Long id) {

        User user =
                userService.getUserById(id);

        if (user != null) {

            return ResponseEntity.ok(

                    convertToResponse(user)

            );
        }

        return ResponseEntity

                .status(HttpStatus.NOT_FOUND)

                .body("User not found");
    }


    // GET ALL USERS

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<UserResponse> users =
                userService.getAllUsers()

                        .stream()

                        .map(this::convertToResponse)

                        .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }


    // UPDATE USER

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(

            @PathVariable Long id,

            @RequestBody User user) {

        User updatedUser =
                userService.updateUser(id, user);

        if (updatedUser != null) {

            return ResponseEntity.ok(

                    convertToResponse(updatedUser)

            );
        }

        return ResponseEntity

                .status(HttpStatus.NOT_FOUND)

                .body("User not found");
    }


    // DELETE USER

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(

            @PathVariable Long id) {

        User user =
                userService.getUserById(id);

        if (user == null) {

            return ResponseEntity

                    .status(HttpStatus.NOT_FOUND)

                    .body("User not found");
        }

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }

}