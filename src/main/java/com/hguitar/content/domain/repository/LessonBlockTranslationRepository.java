package com.hguitar.content.domain.repository;

import com.hguitar.common.domain.Language;
import com.hguitar.content.domain.model.LessonBlockTranslation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonBlockTranslationRepository {

    LessonBlockTranslation save(LessonBlockTranslation translation);

    Optional<LessonBlockTranslation> findByBlockIdAndLanguage(UUID blockId, Language language);

    List<LessonBlockTranslation> findByBlockId(UUID blockId);

    void deleteByBlockId(UUID blockId);
}
