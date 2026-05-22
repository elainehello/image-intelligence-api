package com.elainehello.imageintelligenceapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageUploadRequest {

    @NotBlank(message = "Image URL cannot be blank")
    @Pattern(
            regexp = "^(https?://).+\\.(jpg|jpeg|png|gif|webp|bmp)(\\?.*)?$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Must be a valid URL format (jpg, jpeg, png, gif, webp, bmp)"
    )
    private String imageUrl;
}
