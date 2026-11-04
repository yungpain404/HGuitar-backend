package com.hguitar.onboarding.presentation.controller;

import com.hguitar.auth.infrastructure.security.AuthenticatedUser;
import com.hguitar.onboarding.application.dto.request.CompleteOnboardingRequest;
import com.hguitar.onboarding.application.dto.response.OnboardingStatusResponse;
import com.hguitar.onboarding.application.service.OnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Onboarding", description = "Guitar type selection and onboarding")
@RestController
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @Operation(summary = "Get onboarding status", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/status")
    public ResponseEntity<OnboardingStatusResponse> getStatus(
            @AuthenticationPrincipal AuthenticatedUser principal) {
        return ResponseEntity.ok(onboardingService.getStatus(principal.getUserId()));
    }

    @Operation(summary = "Complete onboarding by selecting guitar type",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/complete")
    public ResponseEntity<OnboardingStatusResponse> complete(
            @AuthenticationPrincipal AuthenticatedUser principal,
            @Valid @RequestBody CompleteOnboardingRequest request) {
        return ResponseEntity.ok(onboardingService.complete(principal.getUserId(), request));
    }
}
