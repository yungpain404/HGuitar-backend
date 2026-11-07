package com.hguitar.progress.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.progress.application.dto.response.LessonProgressResponse;
import com.hguitar.progress.application.dto.response.UserProgressResponse;
import com.hguitar.progress.domain.model.LessonProgress;
import com.hguitar.progress.domain.repository.LessonProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final LessonProgressRepository progressRepository;
    private final LessonRepository lessonRepository;

    public ProgressService(LessonProgressRepository progressRepository,
                           LessonRepository lessonRepository) {
        this.progressRepository = progressRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public LessonProgressResponse completeLesson(UUID userId, UUID lessonId, String lang) {
        // Verify lesson exists
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));

        LessonProgress progress = progressRepository
                .findByUserIdAndLessonId(userId, lessonId)
                .orElse(new LessonProgress(UUID.randomUUID(), userId, lessonId,
                        LessonProgress.Status.IN_PROGRESS, null, Instant.now(), Instant.now()));

        progress.complete();
        progress = progressRepository.save(progress);

        return toResponse(progress, lesson, Language.fromCode(lang));
    }

    @Transactional(readOnly = true)
    public UserProgressResponse getUserProgress(UUID userId, String lang) {
        Language language = Language.fromCode(lang);
        List<LessonProgress> progressList = progressRepository.findByUserId(userId);

        // Resolve lesson titles in a single batch query to keep the progress view cheap.
        List<UUID> lessonIds = progressList.stream()
                .map(LessonProgress::getLessonId)
                .toList();
        Map<UUID, Lesson> lessons = lessonRepository.findAllByIds(lessonIds)
                .stream()
                .collect(Collectors.toMap(Lesson::getId, Function.identity()));

        List<LessonProgressResponse> responses = progressList.stream()
                .map(p -> toResponse(p, lessons.get(p.getLessonId()), language))
                .toList();

        long completedCount = progressList.stream()
                .filter(p -> p.getStatus() == LessonProgress.Status.COMPLETED)
                .count();

        return new UserProgressResponse((int) completedCount, responses);
    }

    private LessonProgressResponse toResponse(LessonProgress progress, Lesson lesson, Language language) {
        String title = lesson != null ? lesson.getTitle(language) : "";
        return new LessonProgressResponse(progress.getLessonId(), title,
                progress.getStatus(), progress.getCompletedAt());
    }
}
