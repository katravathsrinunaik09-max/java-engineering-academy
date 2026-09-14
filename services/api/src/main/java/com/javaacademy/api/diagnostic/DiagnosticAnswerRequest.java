package com.javaacademy.api.diagnostic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiagnosticAnswerRequest(

        @NotNull(message = "Question ID is required")
        Long questionId,

        @NotBlank(message = "Answer is required")
        String answer

) {

}