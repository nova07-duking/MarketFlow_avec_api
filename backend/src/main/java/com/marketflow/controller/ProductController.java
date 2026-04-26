package com.marketflow.controller;

import com.marketflow.dto.request.ProductCreateRequest;
import com.marketflow.dto.response.ApiResponse;
import com.marketflow.dto.response.ProductResponse;
import com.marketflow.model.User;
import com.marketflow.security.CurrentUser;
import com.marketflow.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String sourcingModel,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProducts(categoryId, sourcingModel, minPrice, maxPrice, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProduct(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> search(@RequestParam String query, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(productService.searchProducts(query, pageable)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestPart("product") @Valid ProductCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @CurrentUser User seller) {
        return ResponseEntity.ok(ApiResponse.success("Produit créé", productService.createProduct(request, images, seller)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") @Valid ProductCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @CurrentUser User seller) {
        return ResponseEntity.ok(ApiResponse.success("Produit mis à jour", productService.updateProduct(id, request, images, seller)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id, @CurrentUser User seller) {
        productService.deleteProduct(id, seller);
        return ResponseEntity.ok(ApiResponse.success("Produit supprimé", null));
    }
}
