package com.javaacademy.api.diagnostic;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public record DiagnosticQuestionResponse(
        Long id,
        Long skillId,
        String questionText,
        String questionType,
        String difficulty,
        JsonNode options
) {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static DiagnosticQuestionResponse from(
            DiagnosticQuestion question
    ) {
        try {
            JsonNode options = OBJECT_MAPPER.readTree(
                    question.getOptionsJson()
            );

            return new DiagnosticQuestionResponse(
                    question.getId(),
                    question.getSkill().getId(),
                    question.getQuestionText(),
                    question.getQuestionType(),
                    question.getDifficulty(),
                    options
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Unable to parse diagnostic question options",
                    exception
            );
        }
    }
}