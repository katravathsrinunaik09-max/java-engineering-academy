package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import com.javaacademy.api.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticAssessmentController {

    private final DiagnosticAssessmentService diagnosticAssessmentService;
    private final UserService userService;

    public DiagnosticAssessmentController(
            DiagnosticAssessmentService diagnosticAssessmentService,
            UserService userService
    ) {
        this.diagnosticAssessmentService = diagnosticAssessmentService;
        this.userService = userService;
    }

    @PostMapping("/api/diagnostic/assessments")
    @ResponseStatus(HttpStatus.CREATED)
    public DiagnosticAssessment startAssessment(
            Authentication authentication
    ) {
        User user = userService.getByUsername(authentication.getName());

        return diagnosticAssessmentService.startAssessment(user);
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