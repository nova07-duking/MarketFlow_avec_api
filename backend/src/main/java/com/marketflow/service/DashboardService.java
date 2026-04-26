package com.marketflow.service;

import com.marketflow.dto.response.DashboardStats;
import com.marketflow.repository.OrderRepository;
import com.marketflow.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public DashboardService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public DashboardStats getStats(Long sellerId) {
        BigDecimal totalSales = orderRepository.sumTotalBySellerId(sellerId);
        long activeListings = productRepository.countBySellerId(sellerId);
        long totalViews = productRepository.sumSalesBySellerId(sellerId);
        long lowStock = productRepository.findBySellerIdAndStockLessThan(sellerId, 5).size();
        long totalOrders = orderRepository.countOrdersForSeller(sellerId);

        return DashboardStats.builder()
                .totalSales(totalSales != null ? totalSales : BigDecimal.ZERO)
                .productViews(totalViews)
                .activeListings(activeListings)
                .storeRating(4.9)
                .totalOrders(totalOrders)
                .lowStockCount(lowStock)
                .salesGrowth(12.5)
                .viewsGrowth(4.2)
                .build();
    }
}
