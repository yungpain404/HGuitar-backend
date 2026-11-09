package com.hguitar.music.application.service;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.music.application.dto.response.StrummingPatternResponse;
import com.hguitar.music.domain.model.StrummingPattern;
import com.hguitar.music.domain.repository.StrummingPatternRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StrummingPatternService {

    private final StrummingPatternRepository strummingPatternRepository;

    public StrummingPatternService(StrummingPatternRepository strummingPatternRepository) {
        this.strummingPatternRepository = strummingPatternRepository;
    }

    @Transactional(readOnly = true)
    public List<StrummingPatternResponse> getPatterns(String guitarType, String difficulty) {
        List<StrummingPattern> patterns;
        if (guitarType != null && difficulty != null) {
            patterns = strummingPatternRepository.findByGuitarType(guitarType)
                    .stream()
                    .filter(p -> difficulty.equalsIgnoreCase(p.getDifficulty()))
                    .toList();
        } else if (guitarType != null) {
            patterns = strummingPatternRepository.findByGuitarType(guitarType);
        } else if (difficulty != null) {
            patterns = strummingPatternRepository.findByDifficulty(difficulty);
        } else {
            patterns = strummingPatternRepository.findAll();
        }
        return patterns.stream()
                .filter(p -> p.getStatus() == PublicationStatus.PUBLISHED)
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StrummingPatternResponse getPattern(UUID id) {
        return strummingPatternRepository.findById(id)
                .filter(p -> p.getStatus() == PublicationStatus.PUBLISHED)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Strumming pattern not found."));
    }

    @Transactional
    public StrummingPatternResponse createPattern(StrummingPattern pattern) {
        return toResponse(strummingPatternRepository.save(pattern));
    }

    private StrummingPatternResponse toResponse(StrummingPattern p) {
        return new StrummingPatternResponse(
                p.getId(), p.getName(), p.getFormula(), p.getDescription(),
                p.getDifficulty(), p.getGuitarType(), p.getBpmMin(), p.getBpmMax(),
                p.isFormula(), p.getStatus().name(), p.getSortOrder(), p.getCreatedAt());
    }
}
