package com.elainehello.imageintelligenceapi.controller;

import com.elainehello.imageintelligenceapi.dto.ImageAnalysisResponse;
import com.elainehello.imageintelligenceapi.dto.ImageUploadRequest;
import com.elainehello.imageintelligenceapi.service.ImageAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageAnalysisController {

    private final ImageAnalysisService service;

    @PostMapping
    public ResponseEntity<ImageAnalysisResponse> uploadImage(
            @Valid @RequestBody ImageUploadRequest request) {
        log.info("POST /api/images - Received upload request");
        ImageAnalysisResponse response = service.submitImage(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageAnalysisResponse> getById(@PathVariable Long id) {
        log.info("GET /api/images/{}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ImageAnalysisResponse>> getAll() {
        log.info("GET /api/images - listing all analyses");
        return ResponseEntity.ok(service.getAll());
    }
}
