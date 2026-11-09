package com.hguitar.music.application.service;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.music.application.dto.response.StrummingPatternDetailResponse;
import com.hguitar.music.application.dto.response.StrummingInstructionResponse;
import com.hguitar.music.application.dto.response.StrummingPatternResponse;
import com.hguitar.music.domain.model.StrummingInstruction;
import com.hguitar.music.domain.model.StrummingPattern;
import com.hguitar.music.domain.repository.StrummingInstructionRepository;
import com.hguitar.music.domain.repository.StrummingPatternRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StrummingPatternService {

    private final StrummingPatternRepository strummingPatternRepository;
    private final StrummingInstructionRepository strummingInstructionRepository;

    public StrummingPatternService(StrummingPatternRepository strummingPatternRepository,
                                   StrummingInstructionRepository strummingInstructionRepository) {
        this.strummingPatternRepository = strummingPatternRepository;
        this.strummingInstructionRepository = strummingInstructionRepository;
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

    @Transactional(readOnly = true)
    public StrummingPatternDetailResponse getPatternDetail(UUID id, String lang) {
        StrummingPattern pattern = strummingPatternRepository.findById(id)
                .filter(p -> p.getStatus() == PublicationStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Strumming pattern not found."));

        List<StrummingInstruction> instructions = strummingInstructionRepository.findByPatternId(id);

        List<StrummingInstructionResponse> instructionResponses = instructions.stream()
                .map(instr -> new StrummingInstructionResponse(
                        instr.getId(),
                        instr.getStepNumber(),
                        instr.getTitle(lang),
                        instr.getContent(lang),
                        instr.getTip(lang)
                ))
                .toList();

        return new StrummingPatternDetailResponse(
                pattern.getId(), pattern.getName(), pattern.getFormula(), pattern.getDescription(),
                pattern.getDifficulty(), pattern.getGuitarType(), pattern.getBpmMin(), pattern.getBpmMax(),
                pattern.isFormula(), pattern.getStatus().name(), pattern.getSortOrder(), pattern.getCreatedAt(),
                instructionResponses
        );
    }

    @Transactional(readOnly = true)
    public List<StrummingInstructionResponse> getPatternInstructions(UUID id, String lang) {
        // Validate pattern exists and is published.
        strummingPatternRepository.findById(id)
                .filter(p -> p.getStatus() == PublicationStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Strumming pattern not found."));

        return strummingInstructionRepository.findByPatternId(id).stream()
                .map(instr -> new StrummingInstructionResponse(
                        instr.getId(),
                        instr.getStepNumber(),
                        instr.getTitle(lang),
                        instr.getContent(lang),
                        instr.getTip(lang)
                ))
                .toList();
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
