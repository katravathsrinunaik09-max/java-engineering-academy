package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import com.javaacademy.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticAssessmentController {

    private final DiagnosticAssessmentService diagnosticAssessmentService;
    private final DiagnosticResponseService diagnosticResponseService;
    private final UserService userService;

    @Autowired
    public DiagnosticAssessmentController(
            DiagnosticAssessmentService diagnosticAssessmentService,
            DiagnosticResponseService diagnosticResponseService,
            UserService userService
    ) {
        this.diagnosticAssessmentService = diagnosticAssessmentService;
        this.diagnosticResponseService = diagnosticResponseService;
        this.userService = userService;
    }

    public DiagnosticAssessmentController(
            DiagnosticAssessmentService diagnosticAssessmentService,
            UserService userService
    ) {
        this(
                diagnosticAssessmentService,
                null,
                userService
        );
    }

    @PostMapping("/api/diagnostic/assessments")
    @ResponseStatus(HttpStatus.CREATED)
    public DiagnosticAssessmentResponse startAssessment(
            Authentication authentication
    ) {
        User user = userService.getByUsername(authentication.getName());

        return DiagnosticAssessmentResponse.from(
                diagnosticAssessmentService.startAssessment(user)
        );
    }

    @GetMapping("/api/diagnostic/assessments/{id}")
    public DiagnosticAssessmentResponse getAssessmentById(
            @PathVariable Long id
    ) {
        return DiagnosticAssessmentResponse.from(
                diagnosticAssessmentService.getAssessmentById(id)
        );
    }

    @PostMapping("/api/diagnostic/assessments/{assessmentId}/responses")
    @ResponseStatus(HttpStatus.CREATED)
    public DiagnosticResponseResponse submitAnswer(
            @PathVariable Long assessmentId,
            @RequestBody @Validated DiagnosticAnswerRequest request,
            Authentication authentication
    ) {
        User user = userService.getByUsername(authentication.getName());

        return DiagnosticResponseResponse.from(
                diagnosticResponseService.submitAnswer(
                        assessmentId,
                        request.questionId(),
                        request.answer(),
                        user
                )
        );
    }

    @GetMapping("/api/diagnostic/ping")
    public String ping() {
        return "Diagnostic assessment API is running";
    }
}