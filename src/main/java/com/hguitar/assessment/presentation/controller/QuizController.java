package com.hguitar.assessment.presentation.controller;

import com.hguitar.assessment.application.dto.request.SubmitAttemptRequest;
import com.hguitar.assessment.application.dto.response.AttemptResponse;
import com.hguitar.assessment.application.dto.response.QuizResponse;
import com.hguitar.assessment.application.service.QuizService;
import com.hguitar.auth.infrastructure.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Quizzes", description = "Quizzes, attempts, and submissions")
@RestController
@RequestMapping("/api/v1")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @Operation(summary = "Get quiz for a lesson")
    @GetMapping("/lessons/{lessonId}/quiz")
    public ResponseEntity<QuizResponse> getQuizForLesson(
            @PathVariable UUID lessonId,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(quizService.getQuizForLesson(lessonId, lang));
    }

    @Operation(summary = "Start a new quiz attempt",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/quizzes/{quizId}/attempts")
    public ResponseEntity<AttemptResponse> startAttempt(
            @PathVariable UUID quizId,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(quizService.startAttempt(quizId, principal.getUserId()));
    }

    @Operation(summary = "Submit answers for a quiz attempt",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/quiz-attempts/{attemptId}/submit")
    public ResponseEntity<AttemptResponse> submitAttempt(
            @PathVariable UUID attemptId,
            @AuthenticationPrincipal AuthenticatedUser principal,
            @Valid @RequestBody SubmitAttemptRequest request) {
        return ResponseEntity.ok(quizService.submitAttempt(attemptId, principal.getUserId(), request));
    }

    @Operation(summary = "Get quiz attempt result",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/quiz-attempts/{attemptId}")
    public ResponseEntity<AttemptResponse> getAttempt(
            @PathVariable UUID attemptId,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        return ResponseEntity.ok(quizService.getAttempt(attemptId, principal.getUserId()));
    }
}
