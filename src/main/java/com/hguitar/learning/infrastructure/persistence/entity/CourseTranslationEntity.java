package com.hguitar.learning.infrastructure.persistence.entity;

import com.hguitar.common.domain.Language;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "course_translations", schema = "learning")
public class CourseTranslationEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(name = "language", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    protected CourseTranslationEntity() {}

    public CourseTranslationEntity(UUID id, CourseEntity course, Language language, String title, String description) {
        this.id = id;
        this.course = course;
        this.language = language;
        this.title = title;
        this.description = description;
    }

    public UUID getId() { return id; }
    public CourseEntity getCourse() { return course; }
    public Language getLanguage() { return language; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
