package com.gustavosdaniel.backend.admin;

import com.gustavosdaniel.backend.image.ErrorValidateImage;
import com.gustavosdaniel.backend.product.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Atualizando Produto")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage,
             @Valid ProductUpdateRequest productUpdateRequest)
            throws IOException, ErrorValidateImage, ProductNameAlreadyExistsException
    {

        ProductResponse updateProduct = productService.
                updateProduct(productImage, id, productUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletando Produto")
    public ResponseEntity<Valid> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}



