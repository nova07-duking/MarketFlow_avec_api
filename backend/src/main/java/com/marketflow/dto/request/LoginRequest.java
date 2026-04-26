package com.marketflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    public LoginRequest() {}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
