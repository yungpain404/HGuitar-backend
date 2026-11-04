package com.hguitar.auth.application.service;

import com.hguitar.auth.application.dto.request.*;
import com.hguitar.auth.application.dto.response.AuthResponse;
import com.hguitar.auth.domain.model.Role;
import com.hguitar.auth.domain.model.User;
import com.hguitar.auth.domain.repository.RefreshTokenRepository;
import com.hguitar.auth.domain.repository.UserRepository;
import com.hguitar.auth.infrastructure.security.JwtService;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.user.application.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserProfileService userProfileService;
    private final long refreshTokenExpiryMs;

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserProfileService userProfileService,
            @Value("${app.jwt.refresh-token-expiry-ms}") long refreshTokenExpiryMs) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userProfileService = userProfileService;
        this.refreshTokenExpiryMs = refreshTokenExpiryMs;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("An account with this email already exists.");
        }

        String hash = passwordEncoder.encode(request.password());
        User user = new User(UUID.randomUUID(), request.email(), hash,
                true, Set.of(Role.STUDENT), Instant.now(), Instant.now());

        user = userRepository.save(user);
        userProfileService.createProfile(user.getId(), request.displayName());

        return issueTokensFor(user, request.displayName());
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessRuleException("Invalid email or password."));

        if (!user.isActive()) {
            throw new BusinessRuleException("Account is disabled.");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessRuleException("Invalid email or password.");
        }

        String displayName = userProfileService.getDisplayName(user.getId());
        return issueTokensFor(user, displayName);
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        String tokenHash = hashToken(request.refreshToken());
        UUID userId = refreshTokenRepository.findUserIdByTokenHash(tokenHash)
                .orElseThrow(() -> new BusinessRuleException("Refresh token is invalid or expired."));

        refreshTokenRepository.revokeByTokenHash(tokenHash);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        String displayName = userProfileService.getDisplayName(userId);
        return issueTokensFor(user, displayName);
    }

    @Transactional
    public void logout(LogoutRequest request) {
        String tokenHash = hashToken(request.refreshToken());
        refreshTokenRepository.revokeByTokenHash(tokenHash);
    }

    // ── private helpers ──────────────────────────────────────────────────────

    private AuthResponse issueTokensFor(User user, String displayName) {
        List<String> roles = user.getRoles().stream()
                .map(Role::name)
                .sorted()
                .toList();

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), roles);

        String rawRefresh = generateSecureToken();
        String refreshHash = hashToken(rawRefresh);
        Instant expiresAt = Instant.now().plusMillis(refreshTokenExpiryMs);
        refreshTokenRepository.save(user.getId(), refreshHash, expiresAt);

        return new AuthResponse(user.getId(), user.getEmail(), displayName, roles, accessToken, rawRefresh);
    }

    private String generateSecureToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
