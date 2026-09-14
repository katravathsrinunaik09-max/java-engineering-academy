package com.javaacademy.api.diagnostic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticAssessmentController {

    private final DiagnosticAssessmentService diagnosticAssessmentService;

    public DiagnosticAssessmentController(
            DiagnosticAssessmentService diagnosticAssessmentService
    ) {
        this.diagnosticAssessmentService = diagnosticAssessmentService;
    }

    @GetMapping("/api/diagnostic/assessments/{id}")
    public DiagnosticAssessment getAssessmentById(
            @PathVariable Long id
    ) {
        return diagnosticAssessmentService.getAssessmentById(id);
    }

    @GetMapping("/api/diagnostic/ping")
    public String ping() {
        return "Diagnostic assessment API is running";
    }
}