package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.category.Category;
import com.gustavosdaniel.backend.category.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CategoryRepository categoryRepository;

    public AdminController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/saveCategory")
    @Operation(summary = "Criando category")
    public ResponseEntity<Category> createdCategory(@RequestBody Category category) {

        Category savaCategory = categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(savaCategory);
    }
}
