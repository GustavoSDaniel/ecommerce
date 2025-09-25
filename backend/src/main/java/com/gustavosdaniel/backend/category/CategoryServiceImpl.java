package com.gustavosdaniel.backend.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createdCategory(CategoryRequest categoryRequest) {

        Category newCategory = new Category();
        newCategory.setName(categoryRequest.name());
        newCategory.setImageName(categoryRequest.imageName());
        newCategory.setActive(categoryRequest.isActive());

        categoryRepository.save(newCategory);

        return categoryMapper.toCategoryResponse(newCategory);
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {

        Page<Category> allCategorias = categoryRepository.findAll(pageable);

        return allCategorias.map(categoryMapper::toCategoryResponse);

    }
}
