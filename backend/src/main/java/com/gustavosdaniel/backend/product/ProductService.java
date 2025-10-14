package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;
import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductResponse createdProduct(
            ProductCreatedRequest productCreatedRequest,
                                          MultipartFile productImage)
            throws ExceptionProductNameExists, IOException, ErrorValidateImage;

    ProductResponse findById(String id);

    Page<ProductResponse> findByAllProducts(Pageable pageable);

    Page<ProductResponse> findByAllProductsActive(Pageable pageable);

    Page<ProductResponse> searchProducts(Pageable pageable, String name);

    ProductResponse updateProduct(
            MultipartFile productImage, String id, ProductUpdateRequest productUpdateRequest)
            throws IOException, ErrorValidateImage, ProductNameAlreadyExistsException;

    void deleteProduct(String id);
}
