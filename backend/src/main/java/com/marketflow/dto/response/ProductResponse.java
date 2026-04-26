package com.marketflow.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer stock;
    private String sourcingModel;
    private String categoryName;
    private Long categoryId;
    private String sellerName;
    private Long sellerId;
    private Integer totalSales;
    private Integer totalViews;
    private boolean active;
    private List<String> imageUrls;
    private String createdAt;

    public ProductResponse() {}

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
    public String getSourcingModel() { return sourcingModel; }
    public void setSourcingModel(String sourcingModel) { this.sourcingModel = sourcingModel; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public Integer getTotalSales() { return totalSales; }
    public void setTotalSales(Integer totalSales) { this.totalSales = totalSales; }
    public Integer getTotalViews() { return totalViews; }
    public void setTotalViews(Integer totalViews) { this.totalViews = totalViews; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public static ProductResponseBuilder builder() { return new ProductResponseBuilder(); }

    public static class ProductResponseBuilder {
        private ProductResponse r = new ProductResponse();
        public ProductResponseBuilder id(Long id) { r.setId(id); return this; }
        public ProductResponseBuilder name(String n) { r.setName(n); return this; }
        public ProductResponseBuilder description(String d) { r.setDescription(d); return this; }
        public ProductResponseBuilder price(BigDecimal p) { r.setPrice(p); return this; }
        public ProductResponseBuilder originalPrice(BigDecimal op) { r.setOriginalPrice(op); return this; }
        public ProductResponseBuilder stock(Integer s) { r.setStock(s); return this; }
        public ProductResponseBuilder sourcingModel(String sm) { r.setSourcingModel(sm); return this; }
        public ProductResponseBuilder categoryName(String cn) { r.setCategoryName(cn); return this; }
        public ProductResponseBuilder categoryId(Long cid) { r.setCategoryId(cid); return this; }
        public ProductResponseBuilder sellerName(String sn) { r.setSellerName(sn); return this; }
        public ProductResponseBuilder sellerId(Long sid) { r.setSellerId(sid); return this; }
        public ProductResponseBuilder totalSales(Integer ts) { r.setTotalSales(ts); return this; }
        public ProductResponseBuilder totalViews(Integer tv) { r.setTotalViews(tv); return this; }
        public ProductResponseBuilder active(boolean a) { r.setActive(a); return this; }
        public ProductResponseBuilder imageUrls(List<String> urls) { r.setImageUrls(urls); return this; }
        public ProductResponseBuilder createdAt(String ca) { r.setCreatedAt(ca); return this; }
        public ProductResponse build() { return r; }
    }
}
