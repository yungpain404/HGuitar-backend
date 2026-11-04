package com.hguitar.assessment.domain.model;

import java.util.List;
import java.util.UUID;

public class Question {

    private final UUID id;
    private final UUID quizId;
    private final int sortOrder;
    private final String textEn;
    private final String textVi;
    private final List<Answer> answers;

    public Question(UUID id, UUID quizId, int sortOrder, String textEn, String textVi,
                    List<Answer> answers) {
        this.id = id;
        this.quizId = quizId;
        this.sortOrder = sortOrder;
        this.textEn = textEn;
        this.textVi = textVi;
        this.answers = answers;
    }

    public UUID getId() { return id; }
    public UUID getQuizId() { return quizId; }
    public int getSortOrder() { return sortOrder; }
    public String getTextEn() { return textEn; }
    public String getTextVi() { return textVi; }
    public List<Answer> getAnswers() { return answers; }

    public boolean isCorrectAnswer(UUID answerId) {
        return answers.stream()
                .anyMatch(a -> a.getId().equals(answerId) && a.isCorrect());
    }
}
