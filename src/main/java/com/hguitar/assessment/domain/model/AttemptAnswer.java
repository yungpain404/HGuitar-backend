package com.hguitar.assessment.domain.model;

import java.util.UUID;

public class AttemptAnswer {

    private final UUID id;
    private final UUID attemptId;
    private final UUID questionId;
    private final UUID selectedAnswerId;
    private final boolean correct;

    public AttemptAnswer(UUID id, UUID attemptId, UUID questionId,
                         UUID selectedAnswerId, boolean correct) {
        this.id = id;
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.selectedAnswerId = selectedAnswerId;
        this.correct = correct;
    }

    public UUID getId() { return id; }
    public UUID getAttemptId() { return attemptId; }
    public UUID getQuestionId() { return questionId; }
    public UUID getSelectedAnswerId() { return selectedAnswerId; }
    public boolean isCorrect() { return correct; }
}
