package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.category.Category;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreatedRequest(

        @NotBlank(message = "Nome do produto é obrigatório")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String name,

        @NotBlank(message = "A descrição do produto é obrigatório")
        String description,

        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço deve ser um valor positivo")
        BigDecimal price,

        @NotNull(message = "O estoque é obrigatório")
        @PositiveOrZero(message = "O estoque não pode ser negativo")
        Integer stock,

        @NotNull(message = "O ID da categoria é obrigatória")
        Integer categoryId
) {
}
