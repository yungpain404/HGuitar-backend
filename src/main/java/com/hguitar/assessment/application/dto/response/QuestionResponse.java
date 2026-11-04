package com.hguitar.assessment.application.dto.response;

import java.util.List;
import java.util.UUID;

public record QuestionResponse(
        UUID id,
        int sortOrder,
        String text,
        List<AnswerResponse> answers
) {}
