package com.elainehello.imageintelligenceapi.mapper;

import com.elainehello.imageintelligenceapi.dto.ImageAnalysisResponse;
import com.elainehello.imageintelligenceapi.model.ImageAnalysis;
import org.springframework.stereotype.Component;

@Component
public class ImageAnalysisMapper {

    public ImageAnalysisResponse toResponseDto(ImageAnalysis entity) {
        return ImageAnalysisResponse.builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .status(entity.getStatus())
                .prediction(entity.getPrediction())
                .confidence(entity.getConfidence())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getCreatedAt())
                .build();
    }

    public ImageAnalysis toEntity(String imageUrl) {
        return ImageAnalysis.builder()
                .imageUrl(imageUrl)
                .build();
    }
}
