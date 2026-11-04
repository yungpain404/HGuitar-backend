package com.hguitar.assessment.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SubmitAttemptRequest(
        @NotEmpty(message = "Answers list must not be empty")
        List<@Valid AnswerEntry> answers
) {
    public record AnswerEntry(
            @NotNull(message = "Question ID is required")
            UUID questionId,
            @NotNull(message = "Selected answer ID is required")
            UUID selectedAnswerId
    ) {}
}
