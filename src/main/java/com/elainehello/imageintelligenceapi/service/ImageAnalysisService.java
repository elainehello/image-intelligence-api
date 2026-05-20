package com.elainehello.imageintelligenceapi.service;

import com.elainehello.imageintelligenceapi.client.HuggingFaceClient;
import com.elainehello.imageintelligenceapi.dto.ImageAnalysisResponse;
import com.elainehello.imageintelligenceapi.dto.ImageUploadResquest;
import com.elainehello.imageintelligenceapi.exception.ImageNotFoundException;
import com.elainehello.imageintelligenceapi.mapper.ImageAnalysisMapper;
import com.elainehello.imageintelligenceapi.model.AnalysisStatus;
import com.elainehello.imageintelligenceapi.model.ImageAnalysis;
import com.elainehello.imageintelligenceapi.repository.ImageAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageAnalysisService {

    private final ImageAnalysisRepository repository;
    private final HuggingFaceClient huggingFaceClient;
    private final ImageAnalysisMapper mapper;

    @Transactional
    public ImageAnalysisResponse submitImage(ImageUploadResquest request) {
        log.info("Submitting image for analysis: {}", request.getImageUrl());

        ImageAnalysis analysis = mapper.toEntity(request.getImageUrl());
        analysis.setStatus(AnalysisStatus.PENDING);
        ImageAnalysis saved = repository.save(analysis);

        triggerAnalysisAsync(saved.getId(), saved.getImageUrl());

        return mapper.toResponseDto(saved);
    }

    @Async
    @Transactional
    public void triggerAnalysisAsync(Long id, String imageUrl) {
        ImageAnalysis analysis = repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));

        try {
            analysis.setStatus((AnalysisStatus.PROCESSING));
            repository.save(analysis);

            String rawResponse = huggingFaceClient.analyzeImage(imageUrl);

            analysis.setPrediction(rawResponse);
            analysis.setStatus(AnalysisStatus.COMPLETED);
            analysis.setConfidence(1.0); // parse from response in Phase 3
        } catch (Exception e) {
            log.error("Analysis failed for image id {}: {}", id, e.getMessage());
            analysis.setStatus(AnalysisStatus.FAILED);
        }

        repository.save(analysis);
    }

    @Transactional(readOnly = true)
    public ImageAnalysisResponse getById(Long id) {
        ImageAnalysis analysis = repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
        return mapper.toResponseDto(analysis);
    }

    @Transactional(readOnly = true)
    public List<ImageAnalysisResponse> getAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
