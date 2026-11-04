package com.hguitar.learning.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.content.domain.repository.LessonBlockRepository;
import com.hguitar.content.domain.repository.LessonBlockTranslationRepository;
import com.hguitar.learning.application.dto.request.CreateLessonRequest;
import com.hguitar.learning.application.dto.request.UpdateLessonRequest;
import com.hguitar.learning.application.dto.response.LessonResponse;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.model.LessonTranslation;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.learning.domain.repository.LessonTranslationRepository;
import com.hguitar.learning.domain.repository.ModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock LessonRepository lessonRepository;
    @Mock LessonTranslationRepository translationRepository;
    @Mock ModuleRepository moduleRepository;
    @Mock LessonBlockRepository blockRepository;
    @Mock LessonBlockTranslationRepository blockTranslationRepository;

    @InjectMocks LessonService service;

    private UUID moduleId;
    private UUID lessonId;
    private Lesson draftLesson;

    @BeforeEach
    void setUp() {
        moduleId = UUID.randomUUID();
        lessonId = UUID.randomUUID();
        draftLesson = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.DRAFT, Instant.now(), Instant.now());
    }

    // -------------------------------------------------------------------------
    // createLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("createLesson — happy path creates lesson and two translations")
    void createLesson_happyPath() {
        when(moduleRepository.existsById(moduleId)).thenReturn(true);
        when(lessonRepository.existsByModuleIdAndSortOrderAndIdNot(eq(moduleId), eq(1), any())).thenReturn(false);
        when(lessonRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.findByLessonId(any())).thenReturn(List.of());

        CreateLessonRequest req = new CreateLessonRequest(
                moduleId, 1, 10,
                "Bài học 1", "Mô tả",
                "Lesson 1", "Description");

        LessonResponse resp = service.createLesson(req);

        assertThat(resp).isNotNull();
        assertThat(resp.status()).isEqualTo(PublicationStatus.DRAFT);
        assertThat(resp.sortOrder()).isEqualTo(1);
        verify(translationRepository, times(2)).save(any());
    }

    @Test
    @DisplayName("createLesson — throws when module does not exist")
    void createLesson_moduleNotFound() {
        when(moduleRepository.existsById(moduleId)).thenReturn(false);

        CreateLessonRequest req = new CreateLessonRequest(
                moduleId, 1, 10, "T", null, "T", null);

        assertThatThrownBy(() -> service.createLesson(req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("createLesson — throws ConflictException on duplicate sortOrder")
    void createLesson_duplicateSortOrder() {
        when(moduleRepository.existsById(moduleId)).thenReturn(true);
        when(lessonRepository.existsByModuleIdAndSortOrderAndIdNot(eq(moduleId), eq(1), any())).thenReturn(true);

        CreateLessonRequest req = new CreateLessonRequest(
                moduleId, 1, 10, "T", null, "T", null);

        assertThatThrownBy(() -> service.createLesson(req))
                .isInstanceOf(ConflictException.class);
    }

    // -------------------------------------------------------------------------
    // publishLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("publishLesson — transitions DRAFT → PUBLISHED")
    void publishLesson_fromDraft() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(draftLesson));
        when(lessonRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.findByLessonId(lessonId)).thenReturn(List.of());

        LessonResponse resp = service.publishLesson(lessonId);

        assertThat(resp.status()).isEqualTo(PublicationStatus.PUBLISHED);
    }

    @Test
    @DisplayName("publishLesson — throws BusinessRuleException when already PUBLISHED")
    void publishLesson_alreadyPublished() {
        Lesson published = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.PUBLISHED, Instant.now(), Instant.now());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(published));

        assertThatThrownBy(() -> service.publishLesson(lessonId))
                .isInstanceOf(BusinessRuleException.class);
    }

    // -------------------------------------------------------------------------
    // archiveLesson / unarchiveLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("archiveLesson — transitions PUBLISHED → ARCHIVED")
    void archiveLesson_fromPublished() {
        Lesson published = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.PUBLISHED, Instant.now(), Instant.now());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(published));
        when(lessonRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.findByLessonId(lessonId)).thenReturn(List.of());

        LessonResponse resp = service.archiveLesson(lessonId);

        assertThat(resp.status()).isEqualTo(PublicationStatus.ARCHIVED);
    }

    @Test
    @DisplayName("unarchiveLesson — transitions ARCHIVED → DRAFT")
    void unarchiveLesson_fromArchived() {
        Lesson archived = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.ARCHIVED, Instant.now(), Instant.now());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(archived));
        when(lessonRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.findByLessonId(lessonId)).thenReturn(List.of());

        LessonResponse resp = service.unarchiveLesson(lessonId);

        assertThat(resp.status()).isEqualTo(PublicationStatus.DRAFT);
    }

    // -------------------------------------------------------------------------
    // deleteLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("deleteLesson — deletes a DRAFT lesson with cascade")
    void deleteLesson_draft() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(draftLesson));
        when(blockRepository.findByLessonId(lessonId)).thenReturn(List.of());

        service.deleteLesson(lessonId);

        verify(lessonRepository).deleteById(lessonId);
        verify(translationRepository).deleteByLessonId(lessonId);
    }

    @Test
    @DisplayName("deleteLesson — throws BusinessRuleException for PUBLISHED lesson")
    void deleteLesson_published_throws() {
        Lesson published = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.PUBLISHED, Instant.now(), Instant.now());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(published));

        assertThatThrownBy(() -> service.deleteLesson(lessonId))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Archive the lesson first");
    }

    // -------------------------------------------------------------------------
    // updateLesson
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updateLesson — throws BusinessRuleException for ARCHIVED lesson")
    void updateLesson_archived_throws() {
        Lesson archived = new Lesson(lessonId, moduleId, 1, 10,
                PublicationStatus.ARCHIVED, Instant.now(), Instant.now());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(archived));

        UpdateLessonRequest req = new UpdateLessonRequest(1, 10, "T", null, "T", null);

        assertThatThrownBy(() -> service.updateLesson(lessonId, req))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("ARCHIVED");
    }
}
