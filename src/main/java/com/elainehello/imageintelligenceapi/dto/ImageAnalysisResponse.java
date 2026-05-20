package com.elainehello.imageintelligenceapi.dto;

import com.elainehello.imageintelligenceapi.model.AnalysisStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageAnalysisResponse {

    private Long id;
    private String imageUrl;
    private AnalysisStatus status;
    private String prediction;
    private Double confidence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
