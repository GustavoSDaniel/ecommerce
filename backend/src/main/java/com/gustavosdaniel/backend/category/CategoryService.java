package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryResponse createdCategory(String name, boolean isActive, MultipartFile imageFile) throws ExceptionCategoryNameExists, IOException, ErrorValidateImage;

    Page<CategoryResponse> getAllCategories(Pageable pageable);

    List<CategoryResponse> searchCategoria(String name);

    void deleteCategory(String name);

}
