package com.hguitar.learning.presentation.controller;

import com.hguitar.learning.application.dto.response.CourseResponse;
import com.hguitar.learning.application.dto.response.ModuleResponse;
import com.hguitar.learning.application.service.LearningQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Courses", description = "Course catalog")
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final LearningQueryService learningQueryService;

    public CourseController(LearningQueryService learningQueryService) {
        this.learningQueryService = learningQueryService;
    }

    @Operation(summary = "List all published courses")
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getCourses(
            @RequestParam(defaultValue = "en") String lang,
            @RequestParam(required = false) String guitarType) {
        return ResponseEntity.ok(learningQueryService.getCourses(lang, guitarType));
    }

    @Operation(summary = "Get a course by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(learningQueryService.getCourse(id, lang));
    }

    @Operation(summary = "Get modules for a course")
    @GetMapping("/{id}/modules")
    public ResponseEntity<List<ModuleResponse>> getModules(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(learningQueryService.getModulesByCourse(id, lang));
    }
}
