package com.hguitar.music.application.service;

import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.music.application.dto.response.ChordPositionResponse;
import com.hguitar.music.application.dto.response.ChordResponse;
import com.hguitar.music.domain.model.Chord;
import com.hguitar.music.domain.repository.ChordRepository;
import com.hguitar.onboarding.domain.model.GuitarType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ChordService {

    private final ChordRepository chordRepository;

    public ChordService(ChordRepository chordRepository) {
        this.chordRepository = chordRepository;
    }

    @Transactional(readOnly = true)
    public List<ChordResponse> getChords(String guitarType) {
        List<Chord> chords;
        if (guitarType != null) {
            GuitarType type = GuitarType.valueOf(guitarType.toUpperCase());
            chords = chordRepository.findByGuitarType(type);
        } else {
            chords = chordRepository.findAll();
        }
        return chords.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ChordResponse getChord(UUID id) {
        return chordRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Chord not found."));
    }

    @Transactional(readOnly = true)
    public List<ChordPositionResponse> getPositions(UUID chordId) {
        Chord chord = chordRepository.findById(chordId)
                .orElseThrow(() -> new ResourceNotFoundException("Chord not found."));
        return chord.getPositions().stream()
                .map(p -> new ChordPositionResponse(p.getId(), p.getPositionNumber(),
                        p.getBaseFret(), p.getFingersJson()))
                .toList();
    }

    private ChordResponse toResponse(Chord c) {
        List<ChordPositionResponse> positions = c.getPositions().stream()
                .map(p -> new ChordPositionResponse(p.getId(), p.getPositionNumber(),
                        p.getBaseFret(), p.getFingersJson()))
                .toList();
        return new ChordResponse(c.getId(), c.getName(), c.getGuitarType(),
                c.getDescription(), positions);
    }
}
