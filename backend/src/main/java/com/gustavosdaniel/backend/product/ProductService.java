package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductCreatedResponse createdProduct(
            ProductCreatedRequest productCreatedRequest,
                                          MultipartFile productImage)
            throws ExceptionProductNameExists, IOException, ErrorValidateImage;


}
