package com.marketflow.repository;

import com.marketflow.model.Product;
import com.marketflow.model.enums.SourcingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findBySellerId(Long sellerId, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findBySourcingModel(SourcingModel sourcingModel, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.active = true " +
           "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
           "AND (:sourcingModel IS NULL OR p.sourcingModel = :sourcingModel) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findWithFilters(
            @Param("categoryId") Long categoryId,
            @Param("sourcingModel") SourcingModel sourcingModel,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Product> search(@Param("query") String query, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.seller.id = :sellerId")
    long countBySellerId(@Param("sellerId") Long sellerId);

    @Query("SELECT COALESCE(SUM(p.totalSales), 0) FROM Product p WHERE p.seller.id = :sellerId")
    long sumSalesBySellerId(@Param("sellerId") Long sellerId);

    List<Product> findBySellerIdAndStockLessThan(Long sellerId, Integer threshold);
}
