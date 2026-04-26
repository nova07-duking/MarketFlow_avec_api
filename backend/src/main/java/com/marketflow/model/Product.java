package com.marketflow.model;

import com.marketflow.model.enums.SourcingModel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourcingModel sourcingModel;

    private Integer totalSales = 0;
    private Integer totalViews = 0;
    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<ProductImage> images = new ArrayList<>();

    public Product() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public SourcingModel getSourcingModel() { return sourcingModel; }
    public void setSourcingModel(SourcingModel sourcingModel) { this.sourcingModel = sourcingModel; }
    public Integer getTotalSales() { return totalSales; }
    public void setTotalSales(Integer totalSales) { this.totalSales = totalSales; }
    public Integer getTotalViews() { return totalViews; }
    public void setTotalViews(Integer totalViews) { this.totalViews = totalViews; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public List<ProductImage> getImages() { return images; }
    public void setImages(List<ProductImage> images) { this.images = images; }

    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);
    }

    public static ProductBuilder builder() { return new ProductBuilder(); }

    public static class ProductBuilder {
        private Product p = new Product();
        public ProductBuilder name(String name) { p.setName(name); return this; }
        public ProductBuilder description(String desc) { p.setDescription(desc); return this; }
        public ProductBuilder price(BigDecimal price) { p.setPrice(price); return this; }
        public ProductBuilder originalPrice(BigDecimal op) { p.setOriginalPrice(op); return this; }
        public ProductBuilder stock(Integer stock) { p.setStock(stock); return this; }
        public ProductBuilder sourcingModel(SourcingModel sm) { p.setSourcingModel(sm); return this; }
        public ProductBuilder category(Category c) { p.setCategory(c); return this; }
        public ProductBuilder seller(User s) { p.setSeller(s); return this; }
        public ProductBuilder active(boolean a) { p.setActive(a); return this; }
        public ProductBuilder totalSales(Integer s) { p.setTotalSales(s); return this; }
        public ProductBuilder totalViews(Integer v) { p.setTotalViews(v); return this; }
        public Product build() { return p; }
    }
}
