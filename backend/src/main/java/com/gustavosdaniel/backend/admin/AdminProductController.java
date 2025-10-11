package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import com.gustavosdaniel.backend.product.ExceptionProductNameExists;
import com.gustavosdaniel.backend.product.ProductCreatedRequest;
import com.gustavosdaniel.backend.product.ProductResponse;
import com.gustavosdaniel.backend.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Criando Produto")
    public ResponseEntity<ProductResponse> createdProduct(
            @Valid ProductCreatedRequest productCreatedRequest,
            @RequestPart("imageFile") MultipartFile imageFile)
            throws IOException, ExceptionProductNameExists, ErrorValidateImage {

        ProductResponse productCreatedResponse = productService.
                createdProduct(productCreatedRequest, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(productCreatedResponse);
    }

}



