package com.gustavosdaniel.backend.category;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toCategory(CategoryResponse categoryResponse);

    CategoryResponse toCategoryResponse(Category category);
}
