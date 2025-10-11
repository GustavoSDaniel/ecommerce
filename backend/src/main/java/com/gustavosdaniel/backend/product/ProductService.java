package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductCreatedResponse createdProduct(
            ProductCreatedRequest productCreatedRequest,
                                          MultipartFile productImage)
            throws ExceptionProductNameExists, IOException, ErrorValidateImage;

    ProductResponseId findById(String id);

    Page<ProductResponseId> findByAllProducts(Pageable pageable);
}
