package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.category.Category;
import com.gustavosdaniel.backend.category.CategoryNotFoundException;
import com.gustavosdaniel.backend.category.CategoryRepository;
import com.gustavosdaniel.backend.image.ErrorValidateImage;
import com.gustavosdaniel.backend.image.ImageService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ProductCreatedResponse createdProduct(ProductCreatedRequest productCreatedRequest,
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
}
