package com.elainehello.imageintelligenceapi.repository;

import com.elainehello.imageintelligenceapi.model.AnalysisStatus;
import com.elainehello.imageintelligenceapi.model.ImageAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageAnalysisRepository extends JpaRepository<ImageAnalysis, Long> {

    List<ImageAnalysis> findByStatus(AnalysisStatus status);

    List<ImageAnalysis> findAllByOrderByCreatedAtDesc();
}
