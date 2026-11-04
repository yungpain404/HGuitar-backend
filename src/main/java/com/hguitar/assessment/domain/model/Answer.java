package com.hguitar.assessment.domain.model;

import java.util.UUID;

public class Answer {

    private final UUID id;
    private final UUID questionId;
    private final String textEn;
    private final String textVi;
    private final boolean correct;

    public Answer(UUID id, UUID questionId, String textEn, String textVi, boolean correct) {
        this.id = id;
        this.questionId = questionId;
        this.textEn = textEn;
        this.textVi = textVi;
        this.correct = correct;
    }

    public UUID getId() { return id; }
    public UUID getQuestionId() { return questionId; }
    public String getTextEn() { return textEn; }
    public String getTextVi() { return textVi; }
    public boolean isCorrect() { return correct; }
}
