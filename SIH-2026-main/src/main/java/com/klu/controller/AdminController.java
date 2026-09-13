package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.entity.Admin;
import com.klu.entity.LearningMaterial;
import com.klu.entity.Quiz;
import com.klu.entity.User;
import com.klu.repository.LearningMaterialRepository;
import com.klu.repository.QuizRepository;
import com.klu.repository.UserRepository;
import com.klu.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LearningMaterialRepository learningMaterialRepository;

    @Autowired
    private QuizRepository quizRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(
            @RequestBody Admin admin) {

        Admin existingAdmin =
                adminService.findByEmail(admin.getEmail());

        if (existingAdmin != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Admin email already exists");
        }

        Admin savedAdmin =
                adminService.registerAdmin(admin);

        return ResponseEntity.ok(savedAdmin);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password) {

        Admin admin =
                adminService.login(email, password);

        if (admin == null) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid email or password");
        }

        return ResponseEntity.ok(admin);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(
            @PathVariable Long id) {

        Admin admin =
                adminService.getAdminById(id);

        if (admin == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(admin);
    }


    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {

        return ResponseEntity.ok(
                adminService.getAllAdmins()
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(
            @PathVariable Long id) {

        if (adminService.getAdminById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        adminService.deleteAdmin(id);

        return ResponseEntity.ok(
                "Admin deleted successfully"
        );
    }


    // ==========================================
    // GET ALL USERS
    // ==========================================

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userRepository.findAll();

        return ResponseEntity.ok(users);
    }


    // ==========================================
    // DELETE USER
    // ==========================================

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }


    // ==========================================
    // GET ALL MATERIALS
    // ==========================================

    @GetMapping("/materials")
    public ResponseEntity<List<LearningMaterial>>
            getAllMaterials() {

        List<LearningMaterial> materials =
                learningMaterialRepository.findAll();

        return ResponseEntity.ok(materials);
    }


    // ==========================================
    // DELETE MATERIAL
    // ==========================================

    @DeleteMapping("/materials/{id}")
    public ResponseEntity<?> deleteMaterial(
            @PathVariable Long id) {

        if (!learningMaterialRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        learningMaterialRepository.deleteById(id);

        return ResponseEntity.ok(
                "Learning material deleted successfully"
        );
    }


    // ==========================================
    // GET ALL QUIZZES
    // ==========================================

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {

        List<Quiz> quizzes = quizRepository.findAll();

        return ResponseEntity.ok(quizzes);
    }
}