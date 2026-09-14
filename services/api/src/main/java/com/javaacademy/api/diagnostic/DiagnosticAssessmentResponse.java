package com.javaacademy.api.diagnostic;

import java.time.OffsetDateTime;

public record DiagnosticAssessmentResponse(
        Long id,
        Long userId,
        String status,
        OffsetDateTime startedAt,
        OffsetDateTime completedAt
) {

    public static DiagnosticAssessmentResponse from(
            DiagnosticAssessment assessment
    ) {
        return new DiagnosticAssessmentResponse(
                assessment.getId(),
                assessment.getUser().getId(),
                assessment.getStatus(),
                assessment.getStartedAt(),
                assessment.getCompletedAt()
        );
    }
}