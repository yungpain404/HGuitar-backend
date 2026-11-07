package com.hguitar.progress.presentation.controller;

import com.hguitar.auth.infrastructure.security.AuthenticatedUser;
import com.hguitar.progress.application.dto.response.UserProgressResponse;
import com.hguitar.progress.application.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Progress", description = "User learning progress")
@RestController
@RequestMapping("/api/v1/users")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @Operation(summary = "Get own learning progress",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me/progress")
    public ResponseEntity<UserProgressResponse> getProgress(
            @AuthenticationPrincipal AuthenticatedUser principal,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(progressService.getUserProgress(principal.getUserId(), lang));
    }
}
