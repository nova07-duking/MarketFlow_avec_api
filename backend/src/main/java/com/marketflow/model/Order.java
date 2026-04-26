package com.marketflow.model;

import com.marketflow.model.enums.DeliveryType;
import com.marketflow.model.enums.OrderStatus;
import com.marketflow.model.enums.PaymentMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime estimatedDelivery;
    private String lastActivity;
    private String serviceLevel;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address shippingAddress;

    public Order() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public DeliveryType getDeliveryType() { return deliveryType; }
    public void setDeliveryType(DeliveryType deliveryType) { this.deliveryType = deliveryType; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    public String getLastActivity() { return lastActivity; }
    public void setLastActivity(String lastActivity) { this.lastActivity = lastActivity; }
    public String getServiceLevel() { return serviceLevel; }
    public void setServiceLevel(String serviceLevel) { this.serviceLevel = serviceLevel; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public Address getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public static OrderBuilder builder() { return new OrderBuilder(); }

    public static class OrderBuilder {
        private Order o = new Order();
        public OrderBuilder orderNumber(String n) { o.setOrderNumber(n); return this; }
        public OrderBuilder buyer(User b) { o.setBuyer(b); return this; }
        public OrderBuilder status(OrderStatus s) { o.setStatus(s); return this; }
        public OrderBuilder paymentMethod(PaymentMethod m) { o.setPaymentMethod(m); return this; }
        public OrderBuilder deliveryType(DeliveryType t) { o.setDeliveryType(t); return this; }
        public OrderBuilder subtotal(BigDecimal s) { o.setSubtotal(s); return this; }
        public OrderBuilder taxAmount(BigDecimal t) { o.setTaxAmount(t); return this; }
        public OrderBuilder shippingCost(BigDecimal s) { o.setShippingCost(s); return this; }
        public OrderBuilder total(BigDecimal t) { o.setTotal(t); return this; }
        public OrderBuilder estimatedDelivery(LocalDateTime ed) { o.setEstimatedDelivery(ed); return this; }
        public OrderBuilder lastActivity(String la) { o.setLastActivity(la); return this; }
        public OrderBuilder serviceLevel(String sl) { o.setServiceLevel(sl); return this; }
        public Order build() { return o; }
    }
}
