package com.marketflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrderCreateRequest {
    @NotBlank(message = "Le mode de paiement est obligatoire")
    private String paymentMethod;

    @NotBlank(message = "Le type de livraison est obligatoire")
    private String deliveryType;

    @NotNull(message = "L'adresse de livraison est obligatoire")
    private AddressRequest address;

    private List<OrderItemRequest> items;

    public OrderCreateRequest() {}

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getDeliveryType() { return deliveryType; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }
    public AddressRequest getAddress() { return address; }
    public void setAddress(AddressRequest address) { this.address = address; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }

    public static class AddressRequest {
        @NotBlank private String fullName;
        @NotBlank private String phone;
        @NotBlank private String street;
        private String complement;
        @NotBlank private String city;
        @NotBlank private String zipCode;
        @NotBlank private String country;

        public AddressRequest() {}

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
    }

    public static class OrderItemRequest {
        @NotNull private Long productId;
        @NotNull private Integer quantity;
        private String size;
        private String color;

        public OrderItemRequest() {}

        public Long getProductId() { return productId; }
        public void setProductId(Long p) { this.productId = p; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer q) { this.quantity = q; }
        public String getSize() { return size; }
        public void setSize(String s) { this.size = s; }
        public String getColor() { return color; }
        public void setColor(String c) { this.color = c; }
    }
}
