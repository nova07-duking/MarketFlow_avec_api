package com.marketflow.controller;

import com.marketflow.dto.request.LoginRequest;
import com.marketflow.dto.request.OtpVerifyRequest;
import com.marketflow.dto.request.RegisterRequest;
import com.marketflow.dto.response.ApiResponse;
import com.marketflow.dto.response.AuthResponse;
import com.marketflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request.getEmail(), request.getFullName(), request.getPhone(), request.getRole());
        return ResponseEntity.ok(ApiResponse.success("Inscription réussie, un code OTP a été envoyé par email", null));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<Void>> sendOtp(@Valid @RequestBody LoginRequest request) {
        authService.sendOtp(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Code OTP envoyé", null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(@Valid @RequestBody OtpVerifyRequest request) {
        AuthResponse response = authService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(ApiResponse.success("Connexion réussie", response));
    }
}
