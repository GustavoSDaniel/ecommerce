package com.gustavosdaniel.backend.category;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record CategoryRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Image is required")
        MultipartFile imageName,

        Boolean isActive
) {
}
