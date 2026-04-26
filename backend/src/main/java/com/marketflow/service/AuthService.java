package com.marketflow.service;

import com.marketflow.dto.response.AuthResponse;
import com.marketflow.model.Cart;
import com.marketflow.model.User;
import com.marketflow.model.enums.UserRole;
import com.marketflow.repository.CartRepository;
import com.marketflow.repository.UserRepository;
import com.marketflow.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final JwtTokenProvider tokenProvider;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, CartRepository cartRepository, 
                       JwtTokenProvider tokenProvider, EmailService emailService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
    }

    private final SecureRandom random = new SecureRandom();

    @Transactional
    public void register(String email, String fullName, String phone, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = User.builder()
                .email(email)
                .fullName(fullName)
                .phone(phone)
                .role(UserRole.valueOf(role.toUpperCase()))
                .verified(false)
                .build();

        userRepository.save(user);
        sendOtp(user);
    }

    @Transactional
    public void sendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        sendOtp(user);
    }

    private void sendOtp(User user) {
        String otp = String.format("%06d", random.nextInt(1000000));
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);
        log.info("OTP envoyé pour : {}", user.getEmail());
    }

    @Transactional
    public AuthResponse verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (user.getOtpCode() == null || !user.getOtpCode().equals(otp)) {
            throw new BadCredentialsException("Code OTP invalide");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("Code OTP expiré");
        }

        user.setVerified(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userRepository.save(user);

        // Créer un panier si l'utilisateur est un acheteur
        if (user.getRole() == UserRole.BUYER && user.getCart() == null) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        String token = tokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }
}
