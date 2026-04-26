package com.marketflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String fileName;

    private Integer position = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductImage() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public static ProductImageBuilder builder() { return new ProductImageBuilder(); }

    public static class ProductImageBuilder {
        private ProductImage pi = new ProductImage();
        public ProductImageBuilder url(String url) { pi.setUrl(url); return this; }
        public ProductImageBuilder fileName(String fn) { pi.setFileName(fn); return this; }
        public ProductImageBuilder position(Integer pos) { pi.setPosition(pos); return this; }
        public ProductImage build() { return pi; }
    }
}
