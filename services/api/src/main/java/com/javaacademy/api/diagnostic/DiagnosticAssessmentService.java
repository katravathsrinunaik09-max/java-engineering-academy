package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class DiagnosticAssessmentService {

    private final DiagnosticAssessmentRepository diagnosticAssessmentRepository;

    public DiagnosticAssessmentService(
            DiagnosticAssessmentRepository diagnosticAssessmentRepository
    ) {
        this.diagnosticAssessmentRepository = diagnosticAssessmentRepository;
    }

    public DiagnosticAssessment startAssessment(User user) {
        DiagnosticAssessment assessment = new DiagnosticAssessment(
                user,
                "IN_PROGRESS",
                OffsetDateTime.now()
        );

        return diagnosticAssessmentRepository.save(assessment);
    }

    public DiagnosticAssessment getAssessmentById(Long id) {
        return diagnosticAssessmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Diagnostic assessment not found: " + id)
                );
    }

    @Transactional(readOnly = true)
    public List<DiagnosticAssessment> getAssessmentsForUser(User user) {
        return diagnosticAssessmentRepository.findByUser(user);
    }

    public DiagnosticAssessment completeAssessment(Long id) {
        DiagnosticAssessment assessment = getAssessmentById(id);

        assessment.complete(OffsetDateTime.now());

        return diagnosticAssessmentRepository.save(assessment);
    }

    public DiagnosticAssessment abandonAssessment(Long id) {
        DiagnosticAssessment assessment = getAssessmentById(id);

        assessment.abandon();

        return diagnosticAssessmentRepository.save(assessment);
    }
}