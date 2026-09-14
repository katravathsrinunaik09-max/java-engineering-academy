package com.javaacademy.api.diagnostic;

public record DiagnosticAnswerRequest(
        Long questionId,
        String answer
) {
}