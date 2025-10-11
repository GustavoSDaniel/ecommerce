package com.gustavosdaniel.backend.product;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toProduct(ProductCreatedRequest productCreatedRequest);

    ProductResponse toProductResponse(Product product);

    ProductResponse toProductResponseId(Product product);


}
