package com.marketflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String street;

    private String complement;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    public Address() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public static AddressBuilder builder() { return new AddressBuilder(); }

    public static class AddressBuilder {
        private Address a = new Address();
        public AddressBuilder order(Order o) { a.setOrder(o); return this; }
        public AddressBuilder fullName(String n) { a.setFullName(n); return this; }
        public AddressBuilder phone(String p) { a.setPhone(p); return this; }
        public AddressBuilder street(String s) { a.setStreet(s); return this; }
        public AddressBuilder complement(String c) { a.setComplement(c); return this; }
        public AddressBuilder city(String c) { a.setCity(c); return this; }
        public AddressBuilder zipCode(String z) { a.setZipCode(z); return this; }
        public AddressBuilder country(String c) { a.setCountry(c); return this; }
        public Address build() { return a; }
    }
}
