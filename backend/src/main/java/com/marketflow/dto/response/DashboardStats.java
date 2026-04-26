package com.marketflow.dto.response;

import java.math.BigDecimal;

public class DashboardStats {
    private BigDecimal totalSales;
    private long productViews;
    private long activeListings;
    private double storeRating;
    private long totalOrders;
    private long lowStockCount;
    private double salesGrowth;
    private double viewsGrowth;

    public DashboardStats() {}

    // Getters / Setters
    public BigDecimal getTotalSales() { return totalSales; }
    public void setTotalSales(BigDecimal totalSales) { this.totalSales = totalSales; }
    public long getProductViews() { return productViews; }
    public void setProductViews(long productViews) { this.productViews = productViews; }
    public long getActiveListings() { return activeListings; }
    public void setActiveListings(long activeListings) { this.activeListings = activeListings; }
    public double getStoreRating() { return storeRating; }
    public void setStoreRating(double storeRating) { this.storeRating = storeRating; }
    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }
    public long getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(long lowStockCount) { this.lowStockCount = lowStockCount; }
    public double getSalesGrowth() { return salesGrowth; }
    public void setSalesGrowth(double salesGrowth) { this.salesGrowth = salesGrowth; }
    public double getViewsGrowth() { return viewsGrowth; }
    public void setViewsGrowth(double viewsGrowth) { this.viewsGrowth = viewsGrowth; }

    public static DashboardStatsBuilder builder() { return new DashboardStatsBuilder(); }

    public static class DashboardStatsBuilder {
        private DashboardStats s = new DashboardStats();
        public DashboardStatsBuilder totalSales(BigDecimal ts) { s.setTotalSales(ts); return this; }
        public DashboardStatsBuilder productViews(long pv) { s.setProductViews(pv); return this; }
        public DashboardStatsBuilder activeListings(long al) { s.setActiveListings(al); return this; }
        public DashboardStatsBuilder storeRating(double sr) { s.setStoreRating(sr); return this; }
        public DashboardStatsBuilder totalOrders(long to) { s.setTotalOrders(to); return this; }
        public DashboardStatsBuilder lowStockCount(long ls) { s.setLowStockCount(ls); return this; }
        public DashboardStatsBuilder salesGrowth(double sg) { s.setSalesGrowth(sg); return this; }
        public DashboardStatsBuilder viewsGrowth(double vg) { s.setViewsGrowth(vg); return this; }
        public DashboardStats build() { return s; }
    }
}
