package com.hguitar.assessment.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "answers", schema = "assessment")
public class AnswerEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @Column(name = "text_en", nullable = false, length = 1000)
    private String textEn;

    @Column(name = "text_vi", length = 1000)
    private String textVi;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    protected AnswerEntity() {}

    public AnswerEntity(UUID id, QuestionEntity question, String textEn, String textVi, boolean correct) {
        this.id = id;
        this.question = question;
        this.textEn = textEn;
        this.textVi = textVi;
        this.correct = correct;
    }

    public UUID getId() { return id; }
    public QuestionEntity getQuestion() { return question; }
    public String getTextEn() { return textEn; }
    public String getTextVi() { return textVi; }
    public boolean isCorrect() { return correct; }
}
