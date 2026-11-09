package com.hguitar.learning.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.application.dto.response.LearningMethodResponse;
import com.hguitar.learning.domain.model.LearningMethod;
import com.hguitar.learning.domain.repository.LearningMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningMethodService {

    private final LearningMethodRepository learningMethodRepository;

    public LearningMethodService(LearningMethodRepository learningMethodRepository) {
        this.learningMethodRepository = learningMethodRepository;
    }

    @Transactional(readOnly = true)
    public List<LearningMethodResponse> listMethods(String difficulty, String lang) {
        Language language = Language.fromCode(lang);
        List<LearningMethod> methods;
        if (difficulty != null) {
            methods = learningMethodRepository.findByDifficulty(difficulty);
        } else {
            methods = learningMethodRepository.findAll();
        }
        return methods.stream()
                .filter(m -> m.getStatus() == PublicationStatus.PUBLISHED)
                .map(m -> toResponse(m, language))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LearningMethodResponse getMethod(UUID id, String lang) {
        Language language = Language.fromCode(lang);
        LearningMethod method = learningMethodRepository.findById(id)
                .filter(m -> m.getStatus() == PublicationStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Learning method not found."));
        return toResponse(method, language);
    }

    private LearningMethodResponse toResponse(LearningMethod m, Language language) {
        return new LearningMethodResponse(
                m.getId(), m.getNameKey(), m.getDifficulty(), m.getTargetGuitar(),
                m.getEstimatedWeeks(), m.getStepsCount(), m.getStatus().name(), m.getSortOrder(),
                m.getCreatedAt(), m.getTitle(language), m.getDescription(language), m.getSteps(language));
    }
}
