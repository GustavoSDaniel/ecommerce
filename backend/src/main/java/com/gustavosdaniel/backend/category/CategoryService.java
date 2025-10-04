package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CategoryCreatedResponse createdCategory(CategoryRequest categoryRequest, MultipartFile imageFile)
            throws ExceptionCategoryNameExists, IOException, ErrorValidateImage;

    Page<CategoryCreatedResponse> getAllCategories(Pageable pageable);

    List<CategorySearchResponse> searchCategoria(String name);

    CategoryUpdateResponse updateCategory(String id, CategoryRequest categoryRequest, MultipartFile imageFile)
            throws CategoryNotFoundException, IOException, ErrorValidateImage, ExceptionCategoryNameExists;

    void deleteCategory(String id);

}
