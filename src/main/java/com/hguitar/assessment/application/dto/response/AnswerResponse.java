package com.hguitar.assessment.application.dto.response;

import java.util.UUID;

/**
 * Answer option returned to students — correct flag is intentionally omitted before submission.
 */
public record AnswerResponse(
        UUID id,
        String text
) {}
