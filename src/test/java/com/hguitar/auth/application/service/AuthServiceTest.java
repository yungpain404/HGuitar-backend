package com.hguitar.auth.application.service;

import com.hguitar.auth.application.dto.FirebaseIdentity;
import com.hguitar.auth.application.dto.response.AuthResponse;
import com.hguitar.auth.domain.model.Role;
import com.hguitar.auth.domain.model.User;
import com.hguitar.auth.domain.repository.RefreshTokenRepository;
import com.hguitar.auth.domain.repository.UserRepository;
import com.hguitar.auth.infrastructure.security.JwtService;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.user.application.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UserRepository userRepository;
    @Mock RefreshTokenRepository refreshTokenRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;
    @Mock UserProfileService userProfileService;

    private AuthService service;

    @BeforeEach
    void setUp() {
        service = new AuthService(userRepository, refreshTokenRepository,
                passwordEncoder, jwtService, userProfileService, 604_800_000L);
    }

    @Test
    @DisplayName("loginWithFirebase — creates a student account on first Google sign-in")
    void loginWithFirebase_createsAccount() {
        FirebaseIdentity identity = new FirebaseIdentity(
                "firebase-uid", "new@example.com", "New Student", "https://example.com/avatar.png");

        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("unusable-hash");
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userProfileService.getDisplayName(any())).thenReturn("New Student");
        when(jwtService.generateAccessToken(any(), eq("new@example.com"), anyList()))
                .thenReturn("access-token");

        AuthResponse response = service.loginWithFirebase(identity);

        assertThat(response.email()).isEqualTo("new@example.com");
        assertThat(response.displayName()).isEqualTo("New Student");
        assertThat(response.roles()).containsExactly(Role.STUDENT.name());
        assertThat(response.accessToken()).isEqualTo("access-token");
        assertThat(response.refreshToken()).isNotBlank();

        verify(userRepository).save(any(User.class));
        verify(userProfileService).createProfile(any(), eq("New Student"),
                eq("https://example.com/avatar.png"));
        verify(refreshTokenRepository).save(any(), anyString(), any(Instant.class));
    }

    @Test
    @DisplayName("loginWithFirebase — reuses an existing account instead of duplicating it")
    void loginWithFirebase_reusesExistingAccount() {
        UUID userId = UUID.randomUUID();
        User existing = new User(userId, "existing@example.com", "hash", true,
                Set.of(Role.STUDENT), Instant.now(), Instant.now());

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existing));
        when(userProfileService.getDisplayName(userId)).thenReturn("Existing Name");
        when(jwtService.generateAccessToken(any(), eq("existing@example.com"), anyList()))
                .thenReturn("access-token");

        AuthResponse response = service.loginWithFirebase(new FirebaseIdentity(
                "firebase-uid", "existing@example.com", "Google Name", null));

        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.displayName()).isEqualTo("Existing Name");
        verify(userRepository, never()).save(any());
        verify(userProfileService, never()).createProfile(any(), anyString(), any());
    }

    @Test
    @DisplayName("loginWithFirebase — rejects a disabled account")
    void loginWithFirebase_disabledAccount() {
        User disabled = new User(UUID.randomUUID(), "blocked@example.com", "hash", false,
                Set.of(Role.STUDENT), Instant.now(), Instant.now());
        when(userRepository.findByEmail("blocked@example.com")).thenReturn(Optional.of(disabled));

        assertThatThrownBy(() -> service.loginWithFirebase(new FirebaseIdentity(
                "firebase-uid", "blocked@example.com", "Blocked", null)))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("loginWithFirebase — falls back to token display name when profile is blank")
    void loginWithFirebase_blankProfileUsesTokenName() {
        UUID userId = UUID.randomUUID();
        User existing = new User(userId, "name@example.com", "hash", true,
                Set.of(Role.STUDENT), Instant.now(), Instant.now());

        when(userRepository.findByEmail("name@example.com")).thenReturn(Optional.of(existing));
        when(userProfileService.getDisplayName(userId)).thenReturn("  ");
        when(jwtService.generateAccessToken(any(), eq("name@example.com"), anyList()))
                .thenReturn("access-token");

        AuthResponse response = service.loginWithFirebase(new FirebaseIdentity(
                "firebase-uid", "name@example.com", "Google Name", null));

        assertThat(response.displayName()).isEqualTo("Google Name");
    }
}
