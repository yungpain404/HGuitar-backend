package com.hguitar.music.presentation.controller;

import com.hguitar.music.application.dto.response.ChordPositionResponse;
import com.hguitar.music.application.dto.response.ChordResponse;
import com.hguitar.music.application.service.ChordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Chords", description = "Guitar chord library")
@RestController
@RequestMapping("/api/v1/chords")
public class ChordController {

    private final ChordService chordService;

    public ChordController(ChordService chordService) {
        this.chordService = chordService;
    }

    @Operation(summary = "List all chords")
    @GetMapping
    public ResponseEntity<List<ChordResponse>> getChords(
            @RequestParam(required = false) String guitarType,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(chordService.getChords(guitarType, category));
    }

    @Operation(summary = "Get a chord by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ChordResponse> getChord(@PathVariable UUID id) {
        return ResponseEntity.ok(chordService.getChord(id));
    }

    @Operation(summary = "Get fingering positions for a chord")
    @GetMapping("/{id}/positions")
    public ResponseEntity<List<ChordPositionResponse>> getPositions(@PathVariable UUID id) {
        return ResponseEntity.ok(chordService.getPositions(id));
    }
}
