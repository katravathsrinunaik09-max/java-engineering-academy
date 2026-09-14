package com.javaacademy.api.diagnostic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticAssessmentController {

    @GetMapping("/api/diagnostic/ping")
    public String ping() {
        return "Diagnostic assessment API is running";
    }
}