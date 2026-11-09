package com.hguitar.music.presentation.controller;

import com.hguitar.music.application.dto.response.StrummingPatternDetailResponse;
import com.hguitar.music.application.dto.response.StrummingInstructionResponse;
import com.hguitar.music.application.dto.response.StrummingPatternResponse;
import com.hguitar.music.application.service.StrummingPatternService;
import com.hguitar.music.domain.model.StrummingPattern;
import com.hguitar.common.domain.PublicationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Tag(name = "Strumming Patterns", description = "Cách đánh đàn (strumming) kiểu công thức cho người mới")
@RestController
@RequestMapping("/api/v1/strumming-patterns")
public class StrummingPatternController {

    private final StrummingPatternService strummingPatternService;

    public StrummingPatternController(StrummingPatternService strummingPatternService) {
        this.strummingPatternService = strummingPatternService;
    }

    @Operation(summary = "List strumming patterns (public)")
    @GetMapping
    public ResponseEntity<List<StrummingPatternResponse>> listPatterns(
            @RequestParam(required = false) String guitarType,
            @RequestParam(required = false) String difficulty) {
        return ResponseEntity.ok(strummingPatternService.getPatterns(guitarType, difficulty));
    }

    @Operation(summary = "Get a strumming pattern detail with step-by-step instructions")
    @GetMapping("/{id}")
    public ResponseEntity<StrummingPatternDetailResponse> getPatternDetail(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "vi") String lang) {
        return ResponseEntity.ok(strummingPatternService.getPatternDetail(id, lang));
    }

    @Operation(summary = "Get step-by-step instructions for a strumming pattern")
    @GetMapping("/{id}/instructions")
    public ResponseEntity<List<StrummingInstructionResponse>> getPatternInstructions(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "vi") String lang) {
        return ResponseEntity.ok(strummingPatternService.getPatternInstructions(id, lang));
    }

    @Operation(summary = "Create a strumming pattern (admin)")
    @PostMapping
    public ResponseEntity<StrummingPatternResponse> createPattern(@RequestBody StrummingPatternRequest request) {
        StrummingPattern pattern = new StrummingPattern(
                UUID.randomUUID(), request.name(), request.formula(), request.description(),
                request.difficulty(), request.guitarType(), request.bpmMin(), request.bpmMax(),
                request.isFormula(), PublicationStatus.PUBLISHED, request.sortOrder(), Instant.now());
        return ResponseEntity.ok(strummingPatternService.createPattern(pattern));
    }

    public record StrummingPatternRequest(
            String name,
            String formula,
            String description,
            String difficulty,
            String guitarType,
            Integer bpmMin,
            Integer bpmMax,
            boolean isFormula,
            int sortOrder
    ) {}
}
