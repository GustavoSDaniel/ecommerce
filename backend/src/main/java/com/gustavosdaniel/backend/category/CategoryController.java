package com.gustavosdaniel.backend.category;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get all category")
    public ResponseEntity<Page<CategoryCreatedResponse>> getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page -1, size);

        Page<CategoryCreatedResponse> allCategorya = categoryService.getAllCategories(pageable);

        return ResponseEntity.ok(allCategorya);
    }

    @GetMapping
    @Operation(summary = "Pesquisando por categoria")
    public ResponseEntity<List<CategorySearchResponse>> searchCategoria(@RequestParam String name) {

        List<CategorySearchResponse> categorySearch = categoryService.searchCategoria(name);

        return ResponseEntity.ok(categorySearch);
    }
}
