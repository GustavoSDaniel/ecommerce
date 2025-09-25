package com.gustavosdaniel.backend.category;

import java.util.List;

public interface CategoryService {

    Category savaeCategory(Category category);

    List<Category> getAllCategories();
}
