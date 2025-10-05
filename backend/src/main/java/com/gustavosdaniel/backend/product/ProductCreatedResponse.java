package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;


import java.math.BigDecimal;

public record ProductCreatedResponse(

        String id,

        String name,

        String description,

        BigDecimal price,

        Integer stock,

        String imageName,

        ActiveOrInactive isActive,

        String categoryId


) {
}
