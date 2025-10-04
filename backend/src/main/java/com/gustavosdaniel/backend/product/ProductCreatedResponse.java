package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductCreatedResponse(

        String id,

        String name,

        String description,

        BigDecimal price,

        Integer stock,

        MultipartFile imageName,

        ActiveOrInactive isActive,

        String categoryId


) {
}
