package com.elainehello.imageintelligenceapi.mapper;

import com.elainehello.imageintelligenceapi.dto.ImageAnalysisResponse;
import com.elainehello.imageintelligenceapi.model.ImageAnalysis;
import org.springframework.stereotype.Component;

@Component
public class ImageAnalysisMapper {

    public ImageAnalysisResponse toResponse(ImageAnalysis entity) {
        ImageAnalysisResponse response = new ImageAnalysisResponse();
        response.setId(entity.getId());
        response.setImageUrl(entity.getImageUrl());
        response.setStatus(entity.getStatus());
        response.setPrediction(entity.getPrediction());
        response.setConfidence(entity.getConfidence());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    public ImageAnalysis toEntity(String imageUrl) {
        ImageAnalysis analysis = new ImageAnalysis();
        analysis.setImageUrl(imageUrl);
        return analysis;
    }
}