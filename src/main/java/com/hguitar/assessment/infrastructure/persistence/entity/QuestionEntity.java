package com.hguitar.assessment.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions", schema = "assessment")
public class QuestionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quiz;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "text_en", nullable = false, length = 2000)
    private String textEn;

    @Column(name = "text_vi", length = 2000)
    private String textVi;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AnswerEntity> answers = new ArrayList<>();

    protected QuestionEntity() {}

    public QuestionEntity(UUID id, QuizEntity quiz, int sortOrder, String textEn, String textVi) {
        this.id = id;
        this.quiz = quiz;
        this.sortOrder = sortOrder;
        this.textEn = textEn;
        this.textVi = textVi;
    }

    public UUID getId() { return id; }
    public QuizEntity getQuiz() { return quiz; }
    public int getSortOrder() { return sortOrder; }
    public String getTextEn() { return textEn; }
    public String getTextVi() { return textVi; }
    public List<AnswerEntity> getAnswers() { return answers; }
}
