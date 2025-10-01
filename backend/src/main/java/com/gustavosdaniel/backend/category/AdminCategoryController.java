package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Criando category")
    public ResponseEntity<CategoryResponse> createdCategory(
            @RequestParam("name") String name,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("isActive") boolean isActive)
            throws ExceptionCategoryNameExists, IOException, ErrorValidateImage {

        CategoryResponse savaCategory = categoryService.createdCategory(name, isActive, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(savaCategory);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Apagando categoria")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        return ResponseEntity.noContent().build();
    }
}
