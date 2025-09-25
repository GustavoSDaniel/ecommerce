package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.category.Category;
import com.gustavosdaniel.backend.category.CategoryRepository;
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
    public ResponseEntity<Category> savaCategory(@RequestBody Category category) {

        Category savaCategory = categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(savaCategory);
    }
}
