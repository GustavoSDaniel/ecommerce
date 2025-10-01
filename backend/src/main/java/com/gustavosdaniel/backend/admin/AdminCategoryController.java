package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.category.CategoryCreatedResponse;
import com.gustavosdaniel.backend.category.CategoryService;
import com.gustavosdaniel.backend.category.CategoryUpdateResponse;
import com.gustavosdaniel.backend.category.ExceptionCategoryNameExists;
import com.gustavosdaniel.backend.image.ErrorValidateImage;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Criando category")
    public ResponseEntity<CategoryCreatedResponse> createdCategory(
            @RequestParam("name") String name,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("isActive") boolean isActive)
            throws ExceptionCategoryNameExists, IOException, ErrorValidateImage {

        CategoryCreatedResponse savaCategory = categoryService.createdCategory(name, isActive, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(savaCategory);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Atualizando categoria")
    public ResponseEntity<CategoryUpdateResponse> updateCategory(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("isActive") boolean isActive) throws ExceptionCategoryNameExists,
            IOException, ErrorValidateImage {

        CategoryUpdateResponse updateCategory = categoryService
                .updateCategory(id, name, isActive, imageFile);

        return ResponseEntity.status(HttpStatus.OK).body(updateCategory);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Apagando categoria")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        return ResponseEntity.noContent().build();
    }
}
