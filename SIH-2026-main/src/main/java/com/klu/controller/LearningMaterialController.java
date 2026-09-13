package com.klu.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.entity.LearningMaterial;
import com.klu.repository.LearningMaterialRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/materials")
@CrossOrigin(origins = "http://localhost:5173")
public class LearningMaterialController {

    private final LearningMaterialRepository materialRepository;

    public LearningMaterialController(
            LearningMaterialRepository materialRepository) {

        this.materialRepository = materialRepository;
    }


    // ==========================================
    // GET ALL MATERIALS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<LearningMaterial>> getAllMaterials() {

        List<LearningMaterial> materials =
                materialRepository.findAll();

        return ResponseEntity.ok(materials);
    }


    // ==========================================
    // GET MATERIAL BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<LearningMaterial> getMaterialById(
            @PathVariable Long id) {

        return materialRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }


    // ==========================================
    // GET PDF
    // ==========================================

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getMaterialPdf(
            @PathVariable Long id) throws Exception {

        LearningMaterial material =
                materialRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Material not found"));

        Path path = Paths.get(material.getFilePath())
                .toAbsolutePath()
                .normalize();

        Resource resource =
                new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                material.getFileName() +
                                "\""
                )
                .body(resource);
    }
}