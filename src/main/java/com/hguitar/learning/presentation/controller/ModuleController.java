package com.hguitar.learning.presentation.controller;

import com.hguitar.learning.application.dto.response.ModuleResponse;
import com.hguitar.learning.application.service.LearningQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Modules", description = "Course modules")
@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController {

    private final LearningQueryService learningQueryService;

    public ModuleController(LearningQueryService learningQueryService) {
        this.learningQueryService = learningQueryService;
    }

    @Operation(summary = "Get a module by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponse> getModule(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(learningQueryService.getModule(id, lang));
    }
}
