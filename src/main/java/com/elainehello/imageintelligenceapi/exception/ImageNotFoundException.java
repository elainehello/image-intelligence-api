package com.elainehello.imageintelligenceapi.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(Long id) {
        super("Image analysis not found with id: " + id);
    }
}
