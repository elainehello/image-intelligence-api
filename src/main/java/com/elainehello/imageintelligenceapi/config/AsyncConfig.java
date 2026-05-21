package com.elainehello.imageintelligenceapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Async processing enabled for background Hugging Face interface calls
}
