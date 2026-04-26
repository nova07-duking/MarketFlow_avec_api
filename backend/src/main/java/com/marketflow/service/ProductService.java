package com.marketflow.service;

import com.marketflow.dto.request.ProductCreateRequest;
import com.marketflow.dto.response.ProductResponse;
import com.marketflow.exception.BadRequestException;
import com.marketflow.exception.ResourceNotFoundException;
import com.marketflow.model.*;
import com.marketflow.model.enums.SourcingModel;
import com.marketflow.repository.CategoryRepository;
import com.marketflow.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, 
                          FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileStorageService = fileStorageService;
    }

    public Page<ProductResponse> getProducts(Long categoryId, String sourcingModel,
                                              BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        SourcingModel model = null;
        if (sourcingModel != null && !sourcingModel.isEmpty()) {
            try { model = SourcingModel.valueOf(sourcingModel.toUpperCase()); } catch (IllegalArgumentException ignored) {}
        }
        return productRepository.findWithFilters(categoryId, model, minPrice, maxPrice, pageable).map(this::toResponse);
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
        product.setTotalViews(product.getTotalViews() + 1);
        productRepository.save(product);
        return toResponse(product);
    }

    public Page<ProductResponse> searchProducts(String query, Pageable pageable) {
        return productRepository.search(query, pageable).map(this::toResponse);
    }

    public Page<ProductResponse> getSellerProducts(Long sellerId, Pageable pageable) {
        return productRepository.findBySellerId(sellerId, pageable).map(this::toResponse);
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request, List<MultipartFile> images, User seller) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));
        SourcingModel sm = SourcingModel.valueOf(request.getSourcingModel().toUpperCase());

        Product product = Product.builder()
                .name(request.getName()).description(request.getDescription())
                .price(request.getPrice()).originalPrice(request.getOriginalPrice())
                .stock(request.getStock()).sourcingModel(sm)
                .category(category).seller(seller).active(true).totalSales(0).totalViews(0).build();
        product = productRepository.save(product);

        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                if (!images.get(i).isEmpty()) {
                    String fn = fileStorageService.storeFile(images.get(i));
                    product.addImage(ProductImage.builder().url(fileStorageService.getFileUrl(fn)).fileName(fn).position(i).build());
                }
            }
            product = productRepository.save(product);
        }
        return toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductCreateRequest request, List<MultipartFile> newImages, User seller) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
        if (!product.getSeller().getId().equals(seller.getId())) throw new BadRequestException("Non autorisé");
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));

        product.setName(request.getName()); product.setDescription(request.getDescription());
        product.setPrice(request.getPrice()); product.setOriginalPrice(request.getOriginalPrice());
        product.setStock(request.getStock()); product.setSourcingModel(SourcingModel.valueOf(request.getSourcingModel().toUpperCase()));
        product.setCategory(category);

        if (newImages != null) {
            int pos = product.getImages().size();
            for (int i = 0; i < newImages.size(); i++) {
                if (!newImages.get(i).isEmpty()) {
                    String fn = fileStorageService.storeFile(newImages.get(i));
                    product.addImage(ProductImage.builder().url(fileStorageService.getFileUrl(fn)).fileName(fn).position(pos + i).build());
                }
            }
        }
        return toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id, User seller) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
        if (!product.getSeller().getId().equals(seller.getId())) throw new BadRequestException("Non autorisé");
        product.getImages().forEach(img -> fileStorageService.deleteFile(img.getFileName()));
        productRepository.delete(product);
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .price(p.getPrice()).originalPrice(p.getOriginalPrice()).stock(p.getStock())
                .sourcingModel(p.getSourcingModel().name())
                .categoryName(p.getCategory().getName()).categoryId(p.getCategory().getId())
                .sellerName(p.getSeller().getFullName()).sellerId(p.getSeller().getId())
                .totalSales(p.getTotalSales()).totalViews(p.getTotalViews()).active(p.isActive())
                .imageUrls(p.getImages().stream().map(ProductImage::getUrl).toList())
                .createdAt(p.getCreatedAt() != null ? p.getCreatedAt().toString() : null).build();
    }
}
