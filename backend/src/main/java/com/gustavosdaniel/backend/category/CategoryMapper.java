package com.gustavosdaniel.backend.category;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toCategory(CategoryCreatedResponse categoryResponse);

    CategoryCreatedResponse toCategoryResponse(Category Category);

    CategorySearchResponse  toCategorySearchResponse(Category Category);

    CategoryUpdateResponse toCategoryUpdateResponse(Category Category);

}
