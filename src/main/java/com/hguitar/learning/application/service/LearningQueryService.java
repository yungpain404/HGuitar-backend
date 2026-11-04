package com.hguitar.learning.application.service;

import com.hguitar.common.domain.Language;
import com.hguitar.common.domain.PublicationStatus;
import com.hguitar.common.exception.ResourceNotFoundException;
import com.hguitar.learning.application.dto.response.CourseResponse;
import com.hguitar.learning.application.dto.response.LessonResponse;
import com.hguitar.learning.application.dto.response.ModuleResponse;
import com.hguitar.learning.domain.model.Course;
import com.hguitar.learning.domain.model.Lesson;
import com.hguitar.learning.domain.model.Module;
import com.hguitar.learning.domain.repository.CourseRepository;
import com.hguitar.learning.domain.repository.LessonRepository;
import com.hguitar.learning.domain.repository.ModuleRepository;
import com.hguitar.onboarding.domain.model.GuitarType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LearningQueryService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public LearningQueryService(CourseRepository courseRepository,
                                ModuleRepository moduleRepository,
                                LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCourses(String lang, String guitarType) {
        Language language = Language.fromCode(lang);
        List<Course> courses;
        if (guitarType != null) {
            GuitarType type = GuitarType.valueOf(guitarType.toUpperCase());
            courses = courseRepository.findByGuitarTypeAndStatus(type, PublicationStatus.PUBLISHED);
        } else {
            courses = courseRepository.findByStatus(PublicationStatus.PUBLISHED);
        }
        return courses.stream().map(c -> toCourseResponse(c, language)).toList();
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourse(UUID id, String lang) {
        Language language = Language.fromCode(lang);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found."));
        if (course.getStatus() == PublicationStatus.ARCHIVED) {
            throw new ResourceNotFoundException("Course not found.");
        }
        return toCourseResponse(course, language);
    }

    @Transactional(readOnly = true)
    public List<ModuleResponse> getModulesByCourse(UUID courseId, String lang) {
        Language language = Language.fromCode(lang);
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found."));
        return moduleRepository.findByCourseIdOrderBySortOrder(courseId)
                .stream()
                .filter(m -> m.getStatus() == PublicationStatus.PUBLISHED)
                .map(m -> toModuleResponse(m, language))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LessonResponse> getLessonsByModule(UUID moduleId, String lang) {
        Language language = Language.fromCode(lang);
        moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found."));
        return lessonRepository.findByModuleIdOrderBySortOrder(moduleId)
                .stream()
                .filter(l -> l.getStatus() == PublicationStatus.PUBLISHED)
                .map(l -> toLessonResponse(l, language))
                .toList();
    }

    @Transactional(readOnly = true)
    public LessonResponse getLesson(UUID id, String lang) {
        Language language = Language.fromCode(lang);
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));
        if (lesson.getStatus() == PublicationStatus.ARCHIVED) {
            throw new ResourceNotFoundException("Lesson not found.");
        }
        return toLessonResponse(lesson, language);
    }

    // ── mappers ──────────────────────────────────────────────────────────────

    private CourseResponse toCourseResponse(Course c, Language lang) {
        return new CourseResponse(c.getId(), c.getGuitarType(), c.getSortOrder(),
                c.getStatus(), c.getTitle(lang), c.getDescription(lang));
    }

    private ModuleResponse toModuleResponse(Module m, Language lang) {
        return new ModuleResponse(m.getId(), m.getCourseId(), m.getSortOrder(),
                m.getStatus(), m.getTitle(lang), null);
    }

    private LessonResponse toLessonResponse(Lesson l, Language lang) {
        return new LessonResponse(l.getId(), l.getModuleId(), l.getSortOrder(),
                l.getDurationMinutes(), l.getStatus(), l.getTitle(lang), l.getDescription(lang));
    }
}
