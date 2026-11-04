package com.hguitar.content.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.content.application.dto.response.LessonBlockResponse;
import com.hguitar.content.domain.model.LessonBlock;
import com.hguitar.content.domain.repository.LessonBlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ContentService {

    private final LessonBlockRepository blockRepository;

    public ContentService(LessonBlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Transactional(readOnly = true)
    public List<LessonBlockResponse> getBlocksForLesson(UUID lessonId, String lang) {
        Language language = Language.fromCode(lang);
        return blockRepository.findByLessonIdOrderBySortOrder(lessonId)
                .stream()
                .map(b -> toResponse(b, language))
                .toList();
    }

    private LessonBlockResponse toResponse(LessonBlock b, Language language) {
        return new LessonBlockResponse(b.getId(), b.getLessonId(), b.getBlockType(),
                b.getSortOrder(), b.getMediaAssetId(), b.getMetadataJson(),
                b.getContent(language));
    }
}
