package com.marketflow.controller;

import com.marketflow.dto.response.ApiResponse;
import com.marketflow.dto.response.DashboardStats;
import com.marketflow.model.User;
import com.marketflow.security.CurrentUser;
import com.marketflow.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('SELLER')")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStats>> getStats(@CurrentUser User seller) {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getStats(seller.getId())));
    }
}
