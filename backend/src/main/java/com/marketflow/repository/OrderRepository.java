package com.marketflow.repository;

import com.marketflow.model.Order;
import com.marketflow.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByBuyerId(Long buyerId, Pageable pageable);

    @Query("SELECT o FROM Order o JOIN o.items oi WHERE oi.product.seller.id = :sellerId")
    Page<Order> findOrdersForSeller(@Param("sellerId") Long sellerId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Order o JOIN o.items oi " +
           "WHERE oi.product.seller.id = :sellerId AND o.status != 'CANCELLED'")
    BigDecimal sumTotalBySellerId(@Param("sellerId") Long sellerId);

    long countByBuyerId(Long buyerId);

    @Query("SELECT COUNT(DISTINCT o) FROM Order o JOIN o.items oi WHERE oi.product.seller.id = :sellerId")
    long countOrdersForSeller(@Param("sellerId") Long sellerId);
}
