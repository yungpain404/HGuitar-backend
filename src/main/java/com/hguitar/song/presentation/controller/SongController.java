package com.hguitar.song.presentation.controller;

import com.hguitar.song.application.dto.request.CreateSongRequest;
import com.hguitar.song.application.dto.response.SongResponse;
import com.hguitar.song.application.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Songs", description = "Thư viện bài hát dễ đánh (Việt Nam & nước ngoài) cho người mới")
@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Operation(summary = "List easy beginner songs with filters (source, difficulty, language)")
    @GetMapping
    public ResponseEntity<List<SongResponse>> listSongs(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "vi") String lang) {
        return ResponseEntity.ok(songService.listSongs(source, difficulty, lang));
    }

    @Operation(summary = "Get a song detail with chords and sections")
    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSong(@PathVariable UUID id,
                                                @RequestParam(defaultValue = "vi") String lang) {
        return ResponseEntity.ok(songService.getSong(id, lang));
    }

    @Operation(summary = "Create a song (admin)")
    @PostMapping
    public ResponseEntity<SongResponse> createSong(@RequestBody CreateSongRequest request) {
        return ResponseEntity.ok(songService.createSong(request));
    }
}
