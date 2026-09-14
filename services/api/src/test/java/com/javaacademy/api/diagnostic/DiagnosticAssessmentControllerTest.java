package com.javaacademy.api.diagnostic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiagnosticAssessmentControllerTest {

    @Test
    void pingReturnsExpectedMessage() {
        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController();

        String response = controller.ping();

        assertEquals(
                "Diagnostic assessment API is running",
                response
        );
    }
}