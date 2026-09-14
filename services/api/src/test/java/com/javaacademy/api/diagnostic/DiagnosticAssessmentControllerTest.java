package com.javaacademy.api.diagnostic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DiagnosticAssessmentControllerTest {

    @Test
    void pingReturnsExpectedMessage() {
        DiagnosticAssessmentService service =
                mock(DiagnosticAssessmentService.class);

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(service);

        String response = controller.ping();

        assertEquals(
                "Diagnostic assessment API is running",
                response
        );
    }

    @Test
    void getAssessmentByIdReturnsAssessmentFromService() {
        DiagnosticAssessmentService service =
                mock(DiagnosticAssessmentService.class);

        DiagnosticAssessment assessment =
                mock(DiagnosticAssessment.class);

        when(service.getAssessmentById(1L))
                .thenReturn(assessment);

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(service);

        DiagnosticAssessment response =
                controller.getAssessmentById(1L);

        assertEquals(assessment, response);
    }
}