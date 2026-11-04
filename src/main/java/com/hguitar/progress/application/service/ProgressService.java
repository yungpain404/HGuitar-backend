package com.hguitar.progress.application.service;

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
import java.util.UUID;

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
    public LessonProgressResponse completeLesson(UUID userId, UUID lessonId) {
        // Verify lesson exists
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));

        LessonProgress progress = progressRepository
                .findByUserIdAndLessonId(userId, lessonId)
                .orElse(new LessonProgress(UUID.randomUUID(), userId, lessonId,
                        LessonProgress.Status.IN_PROGRESS, null, Instant.now(), Instant.now()));

        progress.complete();
        progress = progressRepository.save(progress);

        return new LessonProgressResponse(progress.getLessonId(),
                progress.getStatus(), progress.getCompletedAt());
    }

    @Transactional(readOnly = true)
    public UserProgressResponse getUserProgress(UUID userId) {
        List<LessonProgress> progressList = progressRepository.findByUserId(userId);

        List<LessonProgressResponse> responses = progressList.stream()
                .map(p -> new LessonProgressResponse(p.getLessonId(), p.getStatus(), p.getCompletedAt()))
                .toList();

        long completedCount = progressList.stream()
                .filter(p -> p.getStatus() == LessonProgress.Status.COMPLETED)
                .count();

        return new UserProgressResponse((int) completedCount, responses);
    }
}
