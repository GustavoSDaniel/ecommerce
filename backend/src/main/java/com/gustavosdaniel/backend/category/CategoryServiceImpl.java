package com.gustavosdaniel.backend.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createdCategory(CategoryRequest categoryRequest) throws ExceptionCategoryNameExists {

        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new ExceptionCategoryNameExists();
        }

        log.warn("Tentativa de criar categoria com nome duplicado {}", categoryRequest.name());

        Category newCategory = new Category();
        newCategory.setName(categoryRequest.name());
        newCategory.setImageName(categoryRequest.imageName());
        newCategory.setActive(categoryRequest.isActive());

        categoryRepository.save(newCategory);

        log.info("Categoria {} criado com sucesso", newCategory);

        return categoryMapper.toCategoryResponse(newCategory);

    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {

        Page<Category> allCategorias = categoryRepository.findAll(pageable);

        return allCategorias.map(categoryMapper::toCategoryResponse);

    }

}
