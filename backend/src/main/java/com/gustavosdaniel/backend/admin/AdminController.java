package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.category.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    @Operation(summary = "Criando category")
    public ResponseEntity<CategoryResponse> createdCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws ExceptionCategoryNameExists {

        CategoryResponse savaCategory = categoryService.createdCategory(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(savaCategory);
    }
}
