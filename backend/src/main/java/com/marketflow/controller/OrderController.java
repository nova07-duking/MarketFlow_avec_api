package com.marketflow.controller;

import com.marketflow.dto.request.OrderCreateRequest;
import com.marketflow.dto.response.ApiResponse;
import com.marketflow.dto.response.OrderResponse;
import com.marketflow.model.User;
import com.marketflow.security.CurrentUser;
import com.marketflow.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderCreateRequest request,
            @CurrentUser User buyer) {
        return ResponseEntity.ok(ApiResponse.success("Commande créée", orderService.createOrder(request, buyer)));
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable String orderNumber) {
        return ResponseEntity.ok(ApiResponse.success(orderService.trackOrder(orderNumber)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getMyOrders(@CurrentUser User buyer, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getBuyerOrders(buyer.getId(), pageable)));
    }
}
