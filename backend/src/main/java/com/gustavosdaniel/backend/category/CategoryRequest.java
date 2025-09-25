package com.gustavosdaniel.backend.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Image is required")
        String imageName,

        Boolean isActive
) {
}
