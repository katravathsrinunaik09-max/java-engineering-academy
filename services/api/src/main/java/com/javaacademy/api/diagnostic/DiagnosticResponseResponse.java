package com.javaacademy.api.diagnostic;

import java.time.OffsetDateTime;

public record DiagnosticResponseResponse(
        Long id,
        Long assessmentId,
        Long questionId,
        String answer,
        Boolean correct,
        OffsetDateTime answeredAt
) {

    public static DiagnosticResponseResponse from(
            DiagnosticResponse response
    ) {
        return new DiagnosticResponseResponse(
                response.getId(),
                response.getAssessment().getId(),
                response.getQuestion().getId(),
                response.getAnswer(),
                response.getCorrect(),
                response.getAnsweredAt()
        );
    }
}