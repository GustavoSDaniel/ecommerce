package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductResponse createdProduct(
            ProductCreatedRequest productCreatedRequest,
                                          MultipartFile productImage)
            throws ExceptionProductNameExists, IOException, ErrorValidateImage;

    ProductResponse findById(String id);

    Page<ProductResponse> findByAllProducts(Pageable pageable);
}
