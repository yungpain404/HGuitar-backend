package com.hguitar.learning.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.application.dto.response.ModuleResponse;
import com.hguitar.learning.domain.model.Module;
import com.hguitar.learning.domain.repository.CourseRepository;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.learning.domain.repository.ModuleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LearningQueryServiceTest {

    @Mock CourseRepository courseRepository;
    @Mock ModuleRepository moduleRepository;
    @Mock LessonRepository lessonRepository;

    @InjectMocks LearningQueryService service;

    private Module module(UUID id, PublicationStatus status) {
        return new Module(
                id,
                UUID.randomUUID(),
                1,
                status,
                Map.of(
                        Language.EN, new Module.ModuleTranslation("Getting Started", "Learn the basics."),
                        Language.VI, new Module.ModuleTranslation("Bắt đầu", "Tìm hiểu những điều cơ bản.")),
                Instant.now(),
                Instant.now());
    }

    @Test
    @DisplayName("getModule — returns localized title and description")
    void getModule_localizedContent() {
        UUID moduleId = UUID.randomUUID();
        when(moduleRepository.findById(moduleId))
                .thenReturn(Optional.of(module(moduleId, PublicationStatus.PUBLISHED)));

        ModuleResponse response = service.getModule(moduleId, "vi");

        assertThat(response.title()).isEqualTo("Bắt đầu");
        assertThat(response.description()).isEqualTo("Tìm hiểu những điều cơ bản.");
    }

    @Test
    @DisplayName("getModule — falls back to English for unsupported language")
    void getModule_languageFallback() {
        UUID moduleId = UUID.randomUUID();
        when(moduleRepository.findById(moduleId))
                .thenReturn(Optional.of(module(moduleId, PublicationStatus.PUBLISHED)));

        ModuleResponse response = service.getModule(moduleId, "fr");

        assertThat(response.title()).isEqualTo("Getting Started");
    }

    @Test
    @DisplayName("getModule — missing module throws ResourceNotFoundException")
    void getModule_missing_throws() {
        UUID moduleId = UUID.randomUUID();
        when(moduleRepository.findById(moduleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getModule(moduleId, "en"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("getModule — archived module is not exposed")
    void getModule_archived_throws() {
        UUID moduleId = UUID.randomUUID();
        when(moduleRepository.findById(moduleId))
                .thenReturn(Optional.of(module(moduleId, PublicationStatus.ARCHIVED)));

        assertThatThrownBy(() -> service.getModule(moduleId, "en"))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
