package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.category.*;
import com.gustavosdaniel.backend.image.ErrorValidateImage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
            @Valid CategoryRequest categoryRequest, @RequestPart("imageFile")MultipartFile imageFile)
            throws ExceptionCategoryNameExists, IOException, ErrorValidateImage {

        CategoryCreatedResponse savaCategory =
                categoryService.createdCategory(categoryRequest,imageFile );

        return ResponseEntity.status(HttpStatus.CREATED).body(savaCategory);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Atualizando categoria")
    public ResponseEntity<CategoryUpdateResponse> updateCategory(
            @PathVariable String id,
            @Valid CategoryRequest categoryRequest, @RequestPart("imageFile")MultipartFile imageFile)
            throws ExceptionCategoryNameExists,
            IOException, ErrorValidateImage {

        CategoryUpdateResponse updateCategory = categoryService
                .updateCategory(id, categoryRequest, imageFile);

        return ResponseEntity.status(HttpStatus.OK).body(updateCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apagando categoria")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
