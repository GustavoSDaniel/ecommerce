package com.gustavosdaniel.backend.product;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisando Produto por ID")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "id") String id) {

        ProductResponse product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    @Validated
    @Operation(summary = "Mostar todos os Produtos")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") int size)
    {

        Pageable pageable = PageRequest.of(page -1, size);
        Page<ProductResponse> products = productService.findByAllProducts(pageable);
        return ResponseEntity.ok(products);

    }

    @GetMapping("/search")
    @Validated
    @Operation(summary = "Pesquisando Produtos pelo nome")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam String name) {

        Pageable pageable = PageRequest.of(page -1, size);
        Page<ProductResponse> products = productService.searchProducts(pageable, name);
        return ResponseEntity.ok(products);
    }
}
