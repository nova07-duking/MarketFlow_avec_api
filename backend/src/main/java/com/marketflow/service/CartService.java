package com.marketflow.service;

import com.marketflow.dto.response.ProductResponse;
import com.marketflow.exception.BadRequestException;
import com.marketflow.exception.ResourceNotFoundException;
import com.marketflow.model.*;
import com.marketflow.repository.CartItemRepository;
import com.marketflow.repository.CartRepository;
import com.marketflow.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, 
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public Map<String, Object> getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<Map<String, Object>> items = cart.getItems().stream().map(item -> {
            Product p = item.getProduct();
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", item.getId()); m.put("productId", p.getId());
            m.put("productName", p.getName()); m.put("price", p.getPrice());
            m.put("quantity", item.getQuantity()); m.put("size", item.getSize());
            m.put("color", item.getColor());
            m.put("imageUrl", p.getImages().isEmpty() ? null : p.getImages().get(0).getUrl());
            m.put("lineTotal", p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return m;
        }).toList();

        BigDecimal subtotal = items.stream()
                .map(i -> (BigDecimal) i.get("lineTotal"))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of("items", items, "itemCount", items.size(), "subtotal", subtotal);
    }

    @Transactional
    public Map<String, Object> addItem(Long userId, Long productId, Integer quantity, String size, String color) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));

        if (product.getStock() < quantity) throw new BadRequestException("Stock insuffisant");

        Optional<CartItem> existing = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem item = CartItem.builder().cart(cart).product(product)
                    .quantity(quantity).size(size).color(color).build();
            cart.addItem(item);
            cartRepository.save(cart);
        }
        return getCart(userId);
    }

    @Transactional
    public Map<String, Object> updateItem(Long userId, Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Article non trouvé"));
        if (quantity <= 0) { cartItemRepository.delete(item); }
        else { item.setQuantity(quantity); cartItemRepository.save(item); }
        return getCart(userId);
    }

    @Transactional
    public Map<String, Object> removeItem(Long userId, Long itemId) {
        cartItemRepository.deleteById(itemId);
        return getCart(userId);
    }

    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder().user(User.builder().id(userId).build()).build()));
    }
}
