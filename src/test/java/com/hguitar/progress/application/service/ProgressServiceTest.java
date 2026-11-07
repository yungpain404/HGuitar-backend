package com.hguitar.progress.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.progress.application.dto.response.LessonProgressResponse;
import com.hguitar.progress.application.dto.response.UserProgressResponse;
import com.hguitar.progress.domain.model.LessonProgress;
import com.hguitar.progress.domain.repository.LessonProgressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgressServiceTest {

    @Mock LessonProgressRepository progressRepository;
    @Mock LessonRepository lessonRepository;

    @InjectMocks ProgressService service;

    private Lesson lesson(UUID id, String titleEn, String titleVi) {
        return new Lesson(
                id,
                UUID.randomUUID(),
                1,
                10,
                PublicationStatus.PUBLISHED,
                Map.of(
                        Language.EN, new Lesson.LessonTranslation(titleEn, "Description"),
                        Language.VI, new Lesson.LessonTranslation(titleVi, "Mô tả")),
                Instant.now(),
                Instant.now());
    }

    @Test
    @DisplayName("completeLesson — creates completed progress with localized title")
    void completeLesson_createsProgress() {
        UUID userId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();

        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lesson(lessonId, "Lesson 1", "Bài 1")));
        when(progressRepository.findByUserIdAndLessonId(userId, lessonId)).thenReturn(Optional.empty());
        when(progressRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LessonProgressResponse response = service.completeLesson(userId, lessonId, "vi");

        assertThat(response.lessonId()).isEqualTo(lessonId);
        assertThat(response.title()).isEqualTo("Bài 1");
        assertThat(response.status()).isEqualTo(LessonProgress.Status.COMPLETED);
        assertThat(response.completedAt()).isNotNull();
        verify(progressRepository).save(any());
    }

    @Test
    @DisplayName("completeLesson — completing twice keeps the original completion timestamp")
    void completeLesson_isIdempotent() {
        UUID userId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        Instant completedAt = Instant.parse("2026-01-02T03:04:05Z");

        when(lessonRepository.findById(lessonId))
                .thenReturn(Optional.of(lesson(lessonId, "Lesson 1", "Bài 1")));
        when(progressRepository.findByUserIdAndLessonId(userId, lessonId))
                .thenReturn(Optional.of(new LessonProgress(
                        UUID.randomUUID(), userId, lessonId,
                        LessonProgress.Status.COMPLETED, completedAt, completedAt, completedAt)));
        when(progressRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        LessonProgressResponse response = service.completeLesson(userId, lessonId, "en");

        assertThat(response.status()).isEqualTo(LessonProgress.Status.COMPLETED);
        assertThat(response.completedAt()).isEqualTo(completedAt);
    }

    @Test
    @DisplayName("completeLesson — unknown lesson throws ResourceNotFoundException")
    void completeLesson_unknownLesson_throws() {
        UUID lessonId = UUID.randomUUID();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.completeLesson(UUID.randomUUID(), lessonId, "en"))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(progressRepository, never()).save(any());
    }

    @Test
    @DisplayName("getUserProgress — resolves lesson titles and counts completed lessons")
    void getUserProgress_resolvesTitles() {
        UUID userId = UUID.randomUUID();
        UUID lesson1Id = UUID.randomUUID();
        UUID lesson2Id = UUID.randomUUID();

        Instant completedAt = Instant.now();
        LessonProgress completed = new LessonProgress(
                UUID.randomUUID(), userId, lesson1Id,
                LessonProgress.Status.COMPLETED, completedAt, completedAt, completedAt);
        LessonProgress started = new LessonProgress(
                UUID.randomUUID(), userId, lesson2Id,
                LessonProgress.Status.IN_PROGRESS, null, completedAt, completedAt);

        when(progressRepository.findByUserId(userId)).thenReturn(List.of(completed, started));
        when(lessonRepository.findAllByIds(anyCollection()))
                .thenReturn(List.of(
                        lesson(lesson1Id, "Lesson 1", "Bài 1"),
                        lesson(lesson2Id, "Lesson 2", "Bài 2")));

        UserProgressResponse response = service.getUserProgress(userId, "en");

        assertThat(response.totalLessonsCompleted()).isEqualTo(1);
        assertThat(response.lessons())
                .extracting(LessonProgressResponse::title)
                .containsExactly("Lesson 1", "Lesson 2");
    }

    @Test
    @DisplayName("getUserProgress — lessons deleted after completion still appear without a title")
    void getUserProgress_missingLesson_usesEmptyTitle() {
        UUID userId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        Instant now = Instant.now();

        when(progressRepository.findByUserId(userId)).thenReturn(List.of(new LessonProgress(
                UUID.randomUUID(), userId, lessonId,
                LessonProgress.Status.COMPLETED, now, now, now)));
        when(lessonRepository.findAllByIds(anyCollection())).thenReturn(List.of());

        UserProgressResponse response = service.getUserProgress(userId, "en");

        assertThat(response.lessons()).hasSize(1);
        assertThat(response.lessons().get(0).title()).isEmpty();
    }
}
