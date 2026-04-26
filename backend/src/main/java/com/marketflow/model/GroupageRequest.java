package com.marketflow.model;

import com.marketflow.model.enums.GroupageStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "groupage_requests")
public class GroupageRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String requestNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(nullable = false)
    private String sector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupageStatus status;

    private Integer volumeUnits;

    @Column(precision = 10, scale = 2)
    private BigDecimal avgUnitPrice;

    @Column(columnDefinition = "TEXT")
    private String contractDetails;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GroupageRequest() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRequestNumber() { return requestNumber; }
    public void setRequestNumber(String requestNumber) { this.requestNumber = requestNumber; }
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public GroupageStatus getStatus() { return status; }
    public void setStatus(GroupageStatus status) { this.status = status; }
    public Integer getVolumeUnits() { return volumeUnits; }
    public void setVolumeUnits(Integer volumeUnits) { this.volumeUnits = volumeUnits; }
    public BigDecimal getAvgUnitPrice() { return avgUnitPrice; }
    public void setAvgUnitPrice(BigDecimal avgUnitPrice) { this.avgUnitPrice = avgUnitPrice; }
    public String getContractDetails() { return contractDetails; }
    public void setContractDetails(String contractDetails) { this.contractDetails = contractDetails; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static GroupageRequestBuilder builder() { return new GroupageRequestBuilder(); }

    public static class GroupageRequestBuilder {
        private GroupageRequest g = new GroupageRequest();
        public GroupageRequestBuilder requestNumber(String n) { g.setRequestNumber(n); return this; }
        public GroupageRequestBuilder seller(User s) { g.setSeller(s); return this; }
        public GroupageRequestBuilder sector(String s) { g.setSector(s); return this; }
        public GroupageRequestBuilder status(GroupageStatus s) { g.setStatus(s); return this; }
        public GroupageRequest build() { return g; }
    }
}
