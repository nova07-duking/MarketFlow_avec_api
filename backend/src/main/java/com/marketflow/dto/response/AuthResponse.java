package com.marketflow.dto.response;

public class AuthResponse {
    private String token;
    private String type;
    private Long userId;
    private String email;
    private String fullName;
    private String role;

    public AuthResponse() {}

    public AuthResponse(String token, String type, Long userId, String email, String fullName, String role) {
        this.token = token;
        this.type = type;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters / Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static AuthResponseBuilder builder() { return new AuthResponseBuilder(); }

    public static class AuthResponseBuilder {
        private String token, type, email, fullName, role;
        private Long userId;
        public AuthResponseBuilder token(String t) { this.token = t; return this; }
        public AuthResponseBuilder type(String t) { this.type = t; return this; }
        public AuthResponseBuilder userId(Long id) { this.userId = id; return this; }
        public AuthResponseBuilder email(String e) { this.email = e; return this; }
        public AuthResponseBuilder fullName(String f) { this.fullName = f; return this; }
        public AuthResponseBuilder role(String r) { this.role = r; return this; }
        public AuthResponse build() { return new AuthResponse(token, type, userId, email, fullName, role); }
    }
}
