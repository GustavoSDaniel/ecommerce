package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(


        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String name,
        String description,
        @Positive(message = "O preço deve ser um valor positivo")
        BigDecimal price,
        @PositiveOrZero(message = "O estoque não pode ser negativo")
        Integer stock,
        ActiveOrInactive activeOrInactive,
        String categoryId
) {
}
