package com.gustavosdaniel.backend.product;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductCreatedResponse(

        String name,

        String description,

        BigDecimal price,

        Integer stock,

        MultipartFile imageName,

        Integer categoryId


) {
}
