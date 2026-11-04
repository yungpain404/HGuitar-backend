package com.hguitar.content.presentation.controller;

import com.hguitar.content.application.dto.response.LessonBlockResponse;
import com.hguitar.content.application.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Lesson Content", description = "Lesson blocks and content")
@RestController
@RequestMapping("/api/v1/lessons/{lessonId}/blocks")
public class LessonBlockController {

    private final ContentService contentService;

    public LessonBlockController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Operation(summary = "Get all content blocks for a lesson")
    @GetMapping
    public ResponseEntity<List<LessonBlockResponse>> getBlocks(
            @PathVariable UUID lessonId,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(contentService.getBlocksForLesson(lessonId, lang));
    }
}
