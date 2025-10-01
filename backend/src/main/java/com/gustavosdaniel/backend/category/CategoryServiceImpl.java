package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import com.gustavosdaniel.backend.image.ImageService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ImageService imageService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ImageService imageService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public CategoryResponse createdCategory(String name, boolean isActive, MultipartFile imageFile)
            throws ExceptionCategoryNameExists, IOException, ErrorValidateImage {

        if (categoryRepository.existsByName(name)) {
            log.warn("Tentativa de criar categoria com nome duplicado {}", name);
            throw new ExceptionCategoryNameExists();
        }

        String imageName = null;

        if (imageFile != null && !imageFile.isEmpty()){
            imageName = imageService.uploadImage(
                    imageFile, "categories", name);
            log.info("Criando categoria com sucesso: {}", imageName);
        }

        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setImageName(imageName);
        newCategory.setActive(isActive);

        categoryRepository.save(newCategory);

        log.info("Categoria {} criado com sucesso", newCategory);

        return categoryMapper.toCategoryResponse(newCategory);

    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {

        Page<Category> allCategorias = categoryRepository.findAll(pageable);

        return allCategorias.map(categoryMapper::toCategoryResponse);

    }

    @Override
    public List<CategoryResponse> searchCategoria(String name) {

        List<Category> searchCategory = categoryRepository.searchByName(name);

        if (searchCategory.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return searchCategory.stream().map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(String name) {

        Category categoryDeleted = categoryRepository.findByName(name)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(categoryDeleted);

        log.info("Categoria '{}' deletada com sucesso.", name);

    }

}
