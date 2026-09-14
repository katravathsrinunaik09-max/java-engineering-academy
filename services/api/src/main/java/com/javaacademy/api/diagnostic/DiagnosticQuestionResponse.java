package com.javaacademy.api.diagnostic;

public record DiagnosticQuestionResponse(
        Long id,
        Long skillId,
        String questionText,
        String questionType,
        String difficulty,
        String optionsJson
) {

    public static DiagnosticQuestionResponse from(
            DiagnosticQuestion question
    ) {
        return new DiagnosticQuestionResponse(
                question.getId(),
                question.getSkill().getId(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getDifficulty(),
                question.getOptionsJson()
        );
    }
}