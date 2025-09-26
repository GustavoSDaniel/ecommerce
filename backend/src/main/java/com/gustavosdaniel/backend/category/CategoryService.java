package com.gustavosdaniel.backend.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryResponse createdCategory(CategoryRequest categoryRequest) throws ExceptionCategoryNameExists;

    Page<CategoryResponse> getAllCategories(Pageable pageable);

}
