package com.hguitar.learning.presentation.controller;

import com.hguitar.auth.infrastructure.security.AuthenticatedUser;
import com.hguitar.learning.application.dto.response.LessonResponse;
import com.hguitar.learning.application.service.LearningQueryService;
import com.hguitar.progress.application.dto.response.LessonProgressResponse;
import com.hguitar.progress.application.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Lessons", description = "Lesson content and completion")
@RestController
@RequestMapping("/api/v1")
public class LessonController {

    private final LearningQueryService learningQueryService;
    private final ProgressService progressService;

    public LessonController(LearningQueryService learningQueryService,
                            ProgressService progressService) {
        this.learningQueryService = learningQueryService;
        this.progressService = progressService;
    }

    @Operation(summary = "Get lessons in a module")
    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<LessonResponse>> getLessons(
            @PathVariable UUID moduleId,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(learningQueryService.getLessonsByModule(moduleId, lang));
    }

    @Operation(summary = "Get a lesson by ID")
    @GetMapping("/lessons/{id}")
    public ResponseEntity<LessonResponse> getLesson(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(learningQueryService.getLesson(id, lang));
    }

    @Operation(summary = "Mark a lesson as complete",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/lessons/{id}/complete")
    public ResponseEntity<LessonProgressResponse> completeLesson(
            @PathVariable UUID id,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        return ResponseEntity.ok(progressService.completeLesson(principal.getUserId(), id));
    }
}
