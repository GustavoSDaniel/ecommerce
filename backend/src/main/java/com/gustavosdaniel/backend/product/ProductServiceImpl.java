package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.category.Category;
import com.gustavosdaniel.backend.category.CategoryNotFoundException;
import com.gustavosdaniel.backend.category.CategoryRepository;
import com.gustavosdaniel.backend.commun.ActiveOrInactive;
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
import java.util.Optional;

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
    @CacheEvict(value = "products-created", allEntries = true)
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
    @Cacheable(value = "products-findId", key = "#id")
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
    @Cacheable(value = "products-ativo", key = "#pageable.pageNumber + " +
            "'-' + #pageable.pageSize + '-' + #pageable.sort.hashCode()")
    public Page<ProductResponse> findByAllProductsActive(Pageable pageable) {
        Page<Product> productsAtivo = productRepository.
                findByActiveOrInactive(ActiveOrInactive.ACTIVE, pageable);

        return productsAtivo.map(productMapper::toProductResponse);
    }


    @Override
    @Cacheable(
            value = "products-search",
            key = "'search:' + #name.toLowerCase() + ':page:' + #pageable.pageNumber + " +
                    "':size:' + #pageable.pageSize + ':sort:' + #pageable.sort.toString()"
    )
    public Page<ProductResponse> searchProducts(Pageable pageable,String name) {

        Page<Product> productSearch = productRepository.searchByName(pageable,name);

        if (productSearch.isEmpty()) {
            throw new ProductNameNotFoundException();
        }

       return productSearch.map(productMapper::toProductResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"products", "products-page"}, allEntries = true )
    public ProductResponse updateProduct(
            MultipartFile productImage, String id, ProductUpdateRequest productUpdateRequest)
            throws IOException, ErrorValidateImage, ProductNameAlreadyExistsException {

        Product existsProduct = productRepository.findById(id).
                orElseThrow(ProductIdNotFoundException::new);

        String imagemAntiga = existsProduct.getImageName();
        String newImageName = imagemAntiga;

        if (productUpdateRequest.categoryId() != null){
            Category category = categoryRepository.findById(productUpdateRequest.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);

            existsProduct.setCategory(category);
        }

        if (productUpdateRequest.name() != null){
            Optional<Product> nameDuplicate = productRepository.
                    findByName(productUpdateRequest.name());

            if (nameDuplicate.isPresent() && !nameDuplicate.get().getId().equals(id)) {
                throw new ProductNameAlreadyExistsException();
            }

            existsProduct.setName(productUpdateRequest.name());
        }

        if (productUpdateRequest.description() != null){
            existsProduct.setDescription(productUpdateRequest.description());
        }

        if (productUpdateRequest.price() != null){

            existsProduct.setPrice(productUpdateRequest.price());
        }

        if (productUpdateRequest.stock() != null){
            existsProduct.setStock(productUpdateRequest.stock());
        }

        if (productUpdateRequest.activeOrInactive() != null){
            existsProduct.setActiveOrInactive(productUpdateRequest.activeOrInactive());
        }

        if (productImage != null && !productImage.isEmpty()){
            newImageName = imageService.uploadImage(
                    productImage, "Product", productUpdateRequest.name() );
            log.info("Nova imagem carregada para produto {}: {}", id, newImageName);
            existsProduct.setImageName(newImageName);
        }

        Product savedProduct = productRepository.save(existsProduct);

        if (newImageName != null && !newImageName.equals(imagemAntiga) && imagemAntiga != null) {
            imageService.deleteImage(imagemAntiga, "Product");
        }

        log.info("Produto '{}' atualizado com sucesso", existsProduct.getName());

        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    @Transactional
    @CacheEvict(value = "product-deleted", allEntries = true)
    public void deleteProduct(String id) {

        Product productDelete = productRepository.findById(id)
                .orElseThrow(ProductIdNotFoundException::new);

        productRepository.delete(productDelete);

        log.info("Produto do ID  '{}', NOME '{}' deletada com sucesso.", id, productDelete.getName());

    }


}
