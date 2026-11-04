package com.hguitar.learning.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.application.dto.request.CreateLessonRequest;
import com.hguitar.learning.application.dto.request.ReorderLessonsRequest;
import com.hguitar.learning.application.dto.request.UpdateLessonRequest;
import com.hguitar.learning.application.dto.response.LessonResponse;
import com.hguitar.learning.application.dto.response.LessonSummaryResponse;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.learning.domain.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public LessonService(LessonRepository lessonRepository,
                         ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    // -------------------------------------------------------------------------
    // Create
    // -------------------------------------------------------------------------

    public LessonResponse createLesson(CreateLessonRequest request) {
        if (!moduleRepository.existsById(request.moduleId())) {
            throw new ResourceNotFoundException("Module", request.moduleId());
        }

        UUID newId = UUID.randomUUID();
        guardSortOrderUnique(request.moduleId(), request.sortOrder(), newId);

        Map<Language, Lesson.LessonTranslation> translations = new EnumMap<>(Language.class);
        if (request.titleVi() != null) {
            translations.put(Language.VI,
                new Lesson.LessonTranslation(request.titleVi(), request.descriptionVi()));
        }
        if (request.titleEn() != null) {
            translations.put(Language.EN,
                new Lesson.LessonTranslation(request.titleEn(), request.descriptionEn()));
        }

        Instant now = Instant.now();
        Lesson lesson = new Lesson(newId, request.moduleId(), request.sortOrder(),
                request.durationMinutes(), PublicationStatus.DRAFT, translations, now, now);
        lesson = lessonRepository.save(lesson);

        return toLessonResponse(lesson, Language.EN);
    }

    // -------------------------------------------------------------------------
    // Read
    // -------------------------------------------------------------------------

    @Transactional(readOnly = true)
    public LessonResponse getLesson(UUID id) {
        return toLessonResponse(requireLesson(id), Language.EN);
    }

    @Transactional(readOnly = true)
    public List<LessonSummaryResponse> getLessonsForModule(UUID moduleId, String lang) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new ResourceNotFoundException("Module", moduleId);
        }
        Language language = Language.fromCode(lang);
        return lessonRepository.findByModuleId(moduleId).stream()
                .map(l -> new LessonSummaryResponse(l.getId(), l.getModuleId(),
                        l.getSortOrder(), l.getDurationMinutes(), l.getStatus(),
                        l.getTitle(language), l.getDescription(language)))
                .toList();
    }

    // -------------------------------------------------------------------------
    // Update
    // -------------------------------------------------------------------------

    public LessonResponse updateLesson(UUID id, UpdateLessonRequest request) {
        Lesson lesson = requireLesson(id);

        if (lesson.getStatus() == PublicationStatus.ARCHIVED) {
            throw new BusinessRuleException("An ARCHIVED lesson cannot be updated. Un-archive it first.");
        }

        if (request.sortOrder() != lesson.getSortOrder()) {
            guardSortOrderUnique(lesson.getModuleId(), request.sortOrder(), id);
        }
        lesson.updateSortOrder(request.sortOrder());
        lesson.updateDuration(request.durationMinutes());

        // Update translations in the map
        Map<Language, Lesson.LessonTranslation> updatedTranslations = new EnumMap<>(lesson.getTranslations());
        if (request.titleVi() != null) {
            updatedTranslations.put(Language.VI,
                new Lesson.LessonTranslation(request.titleVi(), request.descriptionVi()));
        }
        if (request.titleEn() != null) {
            updatedTranslations.put(Language.EN,
                new Lesson.LessonTranslation(request.titleEn(), request.descriptionEn()));
        }

        Lesson updated = new Lesson(lesson.getId(), lesson.getModuleId(), lesson.getSortOrder(),
                lesson.getDurationMinutes(), lesson.getStatus(), updatedTranslations,
                lesson.getCreatedAt(), Instant.now());
        updated = lessonRepository.save(updated);

        return toLessonResponse(updated, Language.EN);
    }

    // -------------------------------------------------------------------------
    // Reorder
    // -------------------------------------------------------------------------

    public void reorderLessons(UUID moduleId, ReorderLessonsRequest request) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new ResourceNotFoundException("Module", moduleId);
        }

        Map<UUID, Lesson> lessonMap = new HashMap<>();
        for (var item : request.items()) {
            Lesson lesson = requireLesson(item.lessonId());
            if (!lesson.getModuleId().equals(moduleId)) {
                throw new BusinessRuleException(
                        "Lesson " + item.lessonId() + " does not belong to module " + moduleId);
            }
            lessonMap.put(item.lessonId(), lesson);
        }

        long distinctOrders = request.items().stream()
                .mapToInt(ReorderLessonsRequest.LessonOrderItem::sortOrder)
                .distinct().count();
        if (distinctOrders != request.items().size()) {
            throw new ConflictException("Duplicate sortOrder values in reorder request.");
        }

        for (var item : request.items()) {
            Lesson lesson = lessonMap.get(item.lessonId());
            lesson.updateSortOrder(item.sortOrder());
            lessonRepository.save(lesson);
        }
    }

    // -------------------------------------------------------------------------
    // Status transitions
    // -------------------------------------------------------------------------

    public LessonResponse publishLesson(UUID id) {
        Lesson lesson = requireLesson(id);
        try {
            lesson.publish();
        } catch (IllegalStateException e) {
            throw new BusinessRuleException(e.getMessage());
        }
        lessonRepository.save(lesson);
        return toLessonResponse(lesson, Language.EN);
    }

    public LessonResponse archiveLesson(UUID id) {
        Lesson lesson = requireLesson(id);
        lesson.archive();
        lessonRepository.save(lesson);
        return toLessonResponse(lesson, Language.EN);
    }

    public LessonResponse unarchiveLesson(UUID id) {
        Lesson lesson = requireLesson(id);
        try {
            lesson.unarchive();
        } catch (IllegalStateException e) {
            throw new BusinessRuleException(e.getMessage());
        }
        lessonRepository.save(lesson);
        return toLessonResponse(lesson, Language.EN);
    }

    // -------------------------------------------------------------------------
    // Delete
    // -------------------------------------------------------------------------

    public void deleteLesson(UUID id) {
        Lesson lesson = requireLesson(id);
        if (!lesson.isDeletable()) {
            throw new BusinessRuleException(
                    "Only DRAFT or ARCHIVED lessons can be deleted. Archive the lesson first.");
        }
        lessonRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Lesson requireLesson(UUID id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", id));
    }

    private void guardSortOrderUnique(UUID moduleId, int sortOrder, UUID excludedId) {
        if (lessonRepository.existsByModuleIdAndSortOrderAndIdNot(moduleId, sortOrder, excludedId)) {
            throw new ConflictException("sortOrder " + sortOrder + " is already used in this module.");
        }
    }

    private LessonResponse toLessonResponse(Lesson l, Language lang) {
        return new LessonResponse(l.getId(), l.getModuleId(), l.getSortOrder(),
                l.getDurationMinutes(), l.getStatus(), l.getTitle(lang), l.getDescription(lang));
    }
}
