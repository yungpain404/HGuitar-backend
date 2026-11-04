package com.hguitar.content.application.service;

import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ConflictException;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.content.application.dto.request.CreateLessonBlockRequest;
import com.hguitar.content.domain.model.ContentBlockType;
import com.hguitar.content.domain.model.LessonBlock;
import com.hguitar.content.domain.repository.LessonBlockRepository;
import com.hguitar.content.domain.repository.LessonBlockTranslationRepository;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.repository.LessonRepository;
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
class LessonBlockServiceTest {

    @Mock LessonBlockRepository blockRepository;
    @Mock LessonBlockTranslationRepository translationRepository;
    @Mock LessonRepository lessonRepository;

    @InjectMocks LessonBlockService service;

    private UUID lessonId;
    private UUID blockId;
    private Lesson draftLesson;
    private LessonBlock block;

    @BeforeEach
    void setUp() {
        lessonId = UUID.randomUUID();
        blockId = UUID.randomUUID();
        draftLesson = new Lesson(lessonId, UUID.randomUUID(), 1, 10,
                PublicationStatus.DRAFT, Instant.now(), Instant.now());
        block = new LessonBlock(blockId, lessonId, ContentBlockType.TEXT, 1,
                null, null, Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("createBlock — happy path saves block and translations")
    void createBlock_happyPath() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(draftLesson));
        when(blockRepository.existsByLessonIdAndSortOrderAndIdNot(eq(lessonId), eq(1), any())).thenReturn(false);
        when(blockRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(translationRepository.findByBlockId(any())).thenReturn(List.of());

        CreateLessonBlockRequest req = new CreateLessonBlockRequest(
                ContentBlockType.TEXT, 1, null, null, "Nội dung", "Content");

        var resp = service.createBlock(lessonId, req);

        assertThat(resp).isNotNull();
        assertThat(resp.blockType()).isEqualTo(ContentBlockType.TEXT);
        verify(translationRepository, times(2)).save(any());
    }

    @Test
    @DisplayName("createBlock — throws for ARCHIVED lesson")
    void createBlock_archivedLesson_throws() {
        Lesson archived = new Lesson(lessonId, UUID.randomUUID(), 1, 10,
                PublicationStatus.ARCHIVED, Instant.now(), Instant.now());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(archived));

        CreateLessonBlockRequest req = new CreateLessonBlockRequest(
                ContentBlockType.TEXT, 1, null, null, "x", "x");

        assertThatThrownBy(() -> service.createBlock(lessonId, req))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("createBlock — throws ConflictException on duplicate sortOrder")
    void createBlock_duplicateSortOrder() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(draftLesson));
        when(blockRepository.existsByLessonIdAndSortOrderAndIdNot(eq(lessonId), eq(1), any())).thenReturn(true);

        CreateLessonBlockRequest req = new CreateLessonBlockRequest(
                ContentBlockType.TEXT, 1, null, null, "x", "x");

        assertThatThrownBy(() -> service.createBlock(lessonId, req))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    @DisplayName("deleteBlock — removes block and translations")
    void deleteBlock_happyPath() {
        when(blockRepository.findById(blockId)).thenReturn(Optional.of(block));

        service.deleteBlock(lessonId, blockId);

        verify(translationRepository).deleteByBlockId(blockId);
        verify(blockRepository).deleteById(blockId);
    }

    @Test
    @DisplayName("deleteBlock — throws when block belongs to different lesson")
    void deleteBlock_wrongLesson() {
        UUID otherLessonId = UUID.randomUUID();
        LessonBlock blockForOtherLesson = new LessonBlock(blockId, otherLessonId,
                ContentBlockType.TEXT, 1, null, null, Instant.now(), Instant.now());
        when(blockRepository.findById(blockId)).thenReturn(Optional.of(blockForOtherLesson));

        assertThatThrownBy(() -> service.deleteBlock(lessonId, blockId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
