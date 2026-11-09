package com.hguitar.learning.presentation.controller;

import com.hguitar.learning.application.dto.response.LearningMethodResponse;
import com.hguitar.learning.application.service.LearningMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Learning Methods", description = "Phương pháp học guitar phổ biến cho người mới bắt đầu")
@RestController
@RequestMapping("/api/v1/learning-methods")
public class LearningMethodController {

    private final LearningMethodService learningMethodService;

    public LearningMethodController(LearningMethodService learningMethodService) {
        this.learningMethodService = learningMethodService;
    }

    @Operation(summary = "List common learning methods for beginners")
    @GetMapping
    public ResponseEntity<List<LearningMethodResponse>> listMethods(
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "vi") String lang) {
        return ResponseEntity.ok(learningMethodService.listMethods(difficulty, lang));
    }

    @Operation(summary = "Get a learning method by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LearningMethodResponse> getMethod(@PathVariable UUID id,
                                                            @RequestParam(defaultValue = "vi") String lang) {
        return ResponseEntity.ok(learningMethodService.getMethod(id, lang));
    }
}
