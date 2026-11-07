package com.hguitar.auth.presentation.controller;

import com.hguitar.auth.application.dto.request.*;
import com.hguitar.auth.application.dto.response.AuthResponse;
import com.hguitar.auth.application.service.AuthService;
import com.hguitar.auth.domain.service.FirebaseTokenVerifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Register, login, Google/Firebase sign-in, refresh, and logout")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final FirebaseTokenVerifier firebaseTokenVerifier;

    public AuthController(AuthService authService,
                          FirebaseTokenVerifier firebaseTokenVerifier) {
        this.authService = authService;
        this.firebaseTokenVerifier = firebaseTokenVerifier;
    }

    @Operation(summary = "Register a new student account")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @Operation(summary = "Login with email and password")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Sign in or create an account with a Firebase/Google ID token")
    @PostMapping("/google")
    public ResponseEntity<AuthResponse> google(@Valid @RequestBody FirebaseLoginRequest request) {
        var identity = firebaseTokenVerifier.verify(request.idToken());
        return ResponseEntity.ok(authService.loginWithFirebase(identity));
    }

    @Operation(summary = "Refresh access token using a valid refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @Operation(summary = "Logout and revoke the refresh token")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
}
