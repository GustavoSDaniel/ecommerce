package com.gustavosdaniel.backend.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toProduct(ProductCreatedRequest productCreatedRequest);

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse toProductResponse(Product product);

}
