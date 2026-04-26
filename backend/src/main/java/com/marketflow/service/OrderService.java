package com.marketflow.service;

import com.marketflow.dto.request.OrderCreateRequest;
import com.marketflow.dto.response.OrderResponse;
import com.marketflow.exception.BadRequestException;
import com.marketflow.exception.ResourceNotFoundException;
import com.marketflow.model.*;
import com.marketflow.model.enums.*;
import com.marketflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final EmailService emailService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, 
                        CartRepository cartRepository, CartItemRepository cartItemRepository, 
                        EmailService emailService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.emailService = emailService;
    }

    private static final BigDecimal TAX_RATE = new BigDecimal("0.20"); // TVA 20%

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, User buyer) {
        Cart cart = cartRepository.findByUserId(buyer.getId())
                .orElseThrow(() -> new BadRequestException("Panier non trouvé"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Votre panier est vide");
        }

        String orderNumber = "MF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .buyer(buyer)
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()))
                .deliveryType(DeliveryType.valueOf(request.getDeliveryType().toUpperCase()))
                .subtotal(BigDecimal.ZERO)
                .taxAmount(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .estimatedDelivery(LocalDateTime.now().plusDays(5))
                .lastActivity("Commande créée")
                .serviceLevel("Standard")
                .build();

        // Adresse
        var addr = request.getAddress();
        Address address = Address.builder()
                .order(order).fullName(addr.getFullName()).phone(addr.getPhone())
                .street(addr.getStreet()).complement(addr.getComplement())
                .city(addr.getCity()).zipCode(addr.getZipCode()).country(addr.getCountry())
                .build();
        order.setShippingAddress(address);

        // Articles depuis le panier
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem ci : cart.getItems()) {
            Product product = ci.getProduct();
            if (product.getStock() < ci.getQuantity()) {
                throw new BadRequestException("Stock insuffisant pour : " + product.getName());
            }
            OrderItem oi = OrderItem.builder()
                    .product(product).quantity(ci.getQuantity())
                    .unitPrice(product.getPrice()).size(ci.getSize()).color(ci.getColor())
                    .build();
            order.addItem(oi);
            subtotal = subtotal.add(product.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
            // Décrémenter le stock
            product.setStock(product.getStock() - ci.getQuantity());
            product.setTotalSales(product.getTotalSales() + ci.getQuantity());
            productRepository.save(product);
        }

        BigDecimal tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal shipping = subtotal.compareTo(new BigDecimal("100")) >= 0 ? BigDecimal.ZERO : new BigDecimal("4.99");
        order.setSubtotal(subtotal);
        order.setTaxAmount(tax);
        order.setShippingCost(shipping);
        order.setTotal(subtotal.add(tax).add(shipping));

        order = orderRepository.save(order);

        // Vider le panier
        cart.getItems().clear();
        cartRepository.save(cart);

        // Email de confirmation
        emailService.sendOrderConfirmation(buyer.getEmail(), orderNumber, order.getTotal().toString());

        return toResponse(order);
    }

    public Page<OrderResponse> getBuyerOrders(Long buyerId, Pageable pageable) {
        return orderRepository.findByBuyerId(buyerId, pageable).map(this::toResponse);
    }

    public Page<OrderResponse> getSellerOrders(Long sellerId, Pageable pageable) {
        return orderRepository.findOrdersForSeller(sellerId, pageable).map(this::toResponse);
    }

    public OrderResponse getOrder(Long id) {
        return toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée")));
    }

    public OrderResponse trackOrder(String orderNumber) {
        return toResponse(orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Commande #" + orderNumber + " non trouvée")));
    }

    @Transactional
    public OrderResponse updateStatus(Long id, String status, User seller) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        order.setLastActivity("Statut mis à jour : " + status);
        return toResponse(orderRepository.save(order));
    }

    private OrderResponse toResponse(Order o) {
        var items = o.getItems().stream().map(oi -> OrderResponse.OrderItemResponse.builder()
                .productId(oi.getProduct().getId()).productName(oi.getProduct().getName())
                .imageUrl(oi.getProduct().getImages().isEmpty() ? null : oi.getProduct().getImages().get(0).getUrl())
                .quantity(oi.getQuantity()).unitPrice(oi.getUnitPrice())
                .size(oi.getSize()).color(oi.getColor()).build()).toList();

        OrderResponse.AddressResponse addr = null;
        if (o.getShippingAddress() != null) {
            var a = o.getShippingAddress();
            addr = OrderResponse.AddressResponse.builder()
                    .fullName(a.getFullName()).phone(a.getPhone()).street(a.getStreet())
                    .complement(a.getComplement()).city(a.getCity()).zipCode(a.getZipCode())
                    .country(a.getCountry()).build();
        }

        return OrderResponse.builder()
                .id(o.getId()).orderNumber(o.getOrderNumber()).status(o.getStatus().name())
                .paymentMethod(o.getPaymentMethod().name()).deliveryType(o.getDeliveryType().name())
                .subtotal(o.getSubtotal()).shippingCost(o.getShippingCost())
                .taxAmount(o.getTaxAmount()).total(o.getTotal())
                .createdAt(o.getCreatedAt() != null ? o.getCreatedAt().toString() : null)
                .estimatedDelivery(o.getEstimatedDelivery() != null ? o.getEstimatedDelivery().toString() : null)
                .lastActivity(o.getLastActivity()).serviceLevel(o.getServiceLevel())
                .items(items).shippingAddress(addr).build();
    }
}
