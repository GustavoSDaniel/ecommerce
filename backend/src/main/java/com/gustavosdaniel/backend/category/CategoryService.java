package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryCreatedResponse createdCategory(CategoryRequest categoryRequest, MultipartFile imageFile)
            throws ExceptionCategoryNameExists, IOException, ErrorValidateImage;

    Page<CategoryCreatedResponse> getAllCategories(Pageable pageable);

    List<CategorySearchResponse> searchCategoria(String name);

    CategoryUpdateResponse updateCategory(Integer id,String name, boolean isActive, MultipartFile imageFile)
            throws CategoryNotFoundException, IOException, ErrorValidateImage, ExceptionCategoryNameExists;

    void deleteCategory(Integer id);

}
