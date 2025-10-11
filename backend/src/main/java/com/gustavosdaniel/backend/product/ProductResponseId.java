package com.gustavosdaniel.backend.product;

import java.math.BigDecimal;

public record ProductResponseId(

        String id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String imageName
) {
}
