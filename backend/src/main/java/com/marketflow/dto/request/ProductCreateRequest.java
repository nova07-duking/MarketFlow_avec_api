package com.marketflow.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductCreateRequest {
    @NotBlank(message = "Le nom du produit est obligatoire")
    private String name;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    private BigDecimal price;

    private BigDecimal originalPrice;

    @NotNull(message = "Le stock est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer stock;

    @NotBlank(message = "Le modèle de sourcing est obligatoire")
    private String sourcingModel;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categoryId;

    public ProductCreateRequest() {}

    // Getters / Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getSourcingModel() { return sourcingModel; }
    public void setSourcingModel(String sourcingModel) { this.sourcingModel = sourcingModel; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
