package com.marketflow.controller;

import com.marketflow.dto.response.ApiResponse;
import com.marketflow.model.User;
import com.marketflow.security.CurrentUser;
import com.marketflow.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCart(@CurrentUser User user) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(user.getId())));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Map<String, Object>>> addItem(
            @CurrentUser User user,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color) {
        return ResponseEntity.ok(ApiResponse.success("Produit ajouté au panier", cartService.addItem(user.getId(), productId, quantity, size, color)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> removeItem(@CurrentUser User user, @PathVariable Long itemId) {
        return ResponseEntity.ok(ApiResponse.success("Produit retiré du panier", cartService.removeItem(user.getId(), itemId)));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateQuantity(
            @CurrentUser User user,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(ApiResponse.success("Quantité mise à jour", cartService.updateItem(user.getId(), itemId, quantity)));
    }
}
