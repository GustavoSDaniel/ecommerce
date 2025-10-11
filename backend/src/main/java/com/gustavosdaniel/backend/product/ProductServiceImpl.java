package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.category.Category;
import com.gustavosdaniel.backend.category.CategoryNotFoundException;
import com.gustavosdaniel.backend.category.CategoryRepository;
import com.gustavosdaniel.backend.image.ErrorValidateImage;
import com.gustavosdaniel.backend.image.ImageService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;
    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper, ImageService imageService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.imageService = imageService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createdProduct(ProductCreatedRequest productCreatedRequest,
                                          MultipartFile productImage)
            throws ExceptionProductNameExists, IOException, ErrorValidateImage {

        if (productRepository.existsByName(productCreatedRequest.name())) {
            log.warn("Tentativa de criar produto com nome duplicado: {}", productCreatedRequest.name());
            throw new ExceptionProductNameExists();
        }

        String imageProduct = null;

        if (productImage != null && !productImage.isEmpty()) {

            imageProduct = imageService.
                    uploadImage(productImage, "Product", productCreatedRequest.name());
            log.info("Imagem do produto carregada com sucesso: {}", imageProduct);
        }

        Category category = categoryRepository.findById(productCreatedRequest.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product();
        newProduct.setName(productCreatedRequest.name());
        newProduct.setDescription(productCreatedRequest.description());
        newProduct.setPrice(productCreatedRequest.price());
        newProduct.setStock(productCreatedRequest.stock());
        newProduct.setImageName(imageProduct);
        newProduct.setActiveOrInactive(productCreatedRequest.activeOrInactive());
        newProduct.setCategory(category);

        Product salvedProduct = productRepository.save(newProduct);

        log.info("Produto criado com sucesso: {}", newProduct);

        return productMapper.toProductResponse(salvedProduct);

    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponse findById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID do produto não pode ser vazio");
        }

        Product product = productRepository.findById(id).orElseThrow(ProductIdNotFoundException::new);

        log.info("Produto encontrado: ID={}, Nome={}", id, product.getName());

        return productMapper.toProductResponse(product);
    }

    @Override
    @Cacheable(value = "products-page", key = "#pageable.pageNumber + " +
            "'-' + #pageable.pageSize + '-' + #pageable.sort.hashCode()")
    public Page<ProductResponse> findByAllProducts(Pageable pageable) {

        Page<Product> productAll = productRepository.findAll(pageable);

        log.debug("Produtos retornados: {} de {} total",
                productAll.getNumberOfElements(), productAll.getTotalElements());

        return productAll.map(productMapper::toProductResponse);

    }

    @Override
    public List<ProductResponse> searchProducts(String name) {

        List<Product> productSearch = productRepository.findByName(name);

        if (productSearch.isEmpty()) {
            throw new ProductNameNotFoundException();
        }

       return productSearch.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }
}
