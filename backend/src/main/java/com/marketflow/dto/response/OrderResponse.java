package com.marketflow.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String status;
    private String paymentMethod;
    private String deliveryType;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal taxAmount;
    private BigDecimal total;
    private String createdAt;
    private String estimatedDelivery;
    private String lastActivity;
    private String serviceLevel;
    private List<OrderItemResponse> items;
    private AddressResponse shippingAddress;

    public OrderResponse() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getDeliveryType() { return deliveryType; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    public String getLastActivity() { return lastActivity; }
    public void setLastActivity(String lastActivity) { this.lastActivity = lastActivity; }
    public String getServiceLevel() { return serviceLevel; }
    public void setServiceLevel(String serviceLevel) { this.serviceLevel = serviceLevel; }
    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }
    public AddressResponse getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(AddressResponse shippingAddress) { this.shippingAddress = shippingAddress; }

    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private String imageUrl;
        private Integer quantity;
        private BigDecimal unitPrice;
        private String size;
        private String color;

        public OrderItemResponse() {}

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }

        public static OrderItemResponseBuilder builder() { return new OrderItemResponseBuilder(); }
        public static class OrderItemResponseBuilder {
            private OrderItemResponse oi = new OrderItemResponse();
            public OrderItemResponseBuilder productId(Long id) { oi.setProductId(id); return this; }
            public OrderItemResponseBuilder productName(String n) { oi.setProductName(n); return this; }
            public OrderItemResponseBuilder imageUrl(String u) { oi.setImageUrl(u); return this; }
            public OrderItemResponseBuilder quantity(Integer q) { oi.setQuantity(q); return this; }
            public OrderItemResponseBuilder unitPrice(BigDecimal p) { oi.setUnitPrice(p); return this; }
            public OrderItemResponseBuilder size(String s) { oi.setSize(s); return this; }
            public OrderItemResponseBuilder color(String c) { oi.setColor(c); return this; }
            public OrderItemResponse build() { return oi; }
        }
    }

    public static class AddressResponse {
        private String fullName, phone, street, complement, city, zipCode, country;
        public AddressResponse() {}
        public String getFullName() { return fullName; }
        public void setFullName(String f) { this.fullName = f; }
        public String getPhone() { return phone; }
        public void setPhone(String p) { this.phone = p; }
        public String getStreet() { return street; }
        public void setStreet(String s) { this.street = s; }
        public String getComplement() { return complement; }
        public void setComplement(String c) { this.complement = c; }
        public String getCity() { return city; }
        public void setCity(String c) { this.city = c; }
        public String getZipCode() { return zipCode; }
        public void setZipCode(String z) { this.zipCode = z; }
        public String getCountry() { return country; }
        public void setCountry(String c) { this.country = c; }

        public static AddressResponseBuilder builder() { return new AddressResponseBuilder(); }
        public static class AddressResponseBuilder {
            private AddressResponse a = new AddressResponse();
            public AddressResponseBuilder fullName(String n) { a.setFullName(n); return this; }
            public AddressResponseBuilder phone(String p) { a.setPhone(p); return this; }
            public AddressResponseBuilder street(String s) { a.setStreet(s); return this; }
            public AddressResponseBuilder complement(String c) { a.setComplement(c); return this; }
            public AddressResponseBuilder city(String c) { a.setCity(c); return this; }
            public AddressResponseBuilder zipCode(String z) { a.setZipCode(z); return this; }
            public AddressResponseBuilder country(String c) { a.setCountry(c); return this; }
            public AddressResponse build() { return a; }
        }
    }

    public static OrderResponseBuilder builder() { return new OrderResponseBuilder(); }
    public static class OrderResponseBuilder {
        private OrderResponse o = new OrderResponse();
        public OrderResponseBuilder id(Long id) { o.setId(id); return this; }
        public OrderResponseBuilder orderNumber(String n) { o.setOrderNumber(n); return this; }
        public OrderResponseBuilder status(String s) { o.setStatus(s); return this; }
        public OrderResponseBuilder paymentMethod(String m) { o.setPaymentMethod(m); return this; }
        public OrderResponseBuilder deliveryType(String t) { o.setDeliveryType(t); return this; }
        public OrderResponseBuilder subtotal(BigDecimal s) { o.setSubtotal(s); return this; }
        public OrderResponseBuilder shippingCost(BigDecimal s) { o.setShippingCost(s); return this; }
        public OrderResponseBuilder taxAmount(BigDecimal t) { o.setTaxAmount(t); return this; }
        public OrderResponseBuilder total(BigDecimal t) { o.setTotal(t); return this; }
        public OrderResponseBuilder createdAt(String c) { o.setCreatedAt(c); return this; }
        public OrderResponseBuilder estimatedDelivery(String e) { o.setEstimatedDelivery(e); return this; }
        public OrderResponseBuilder lastActivity(String la) { o.setLastActivity(la); return this; }
        public OrderResponseBuilder serviceLevel(String sl) { o.setServiceLevel(sl); return this; }
        public OrderResponseBuilder items(List<OrderItemResponse> i) { o.setItems(i); return this; }
        public OrderResponseBuilder shippingAddress(AddressResponse a) { o.setShippingAddress(a); return this; }
        public OrderResponse build() { return o; }
    }
}
