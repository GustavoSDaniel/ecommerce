package com.gustavosdaniel.backend.product;

import java.math.BigDecimal;

public record ProductResponseSearch(

        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String imageName
) {
}
